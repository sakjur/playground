#include <errno.h>
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <string.h>
#include <libssh/libssh.h>
#include <sys/stat.h>

const char* ED25519 = "ed25519";
const char* RSA = "rsa";
const int32_t MAX_PASSWORD_LENGTH = 1024;
const int32_t MIN_PASSWORD_LENGTH = 10;

int main(int argc, char** argv) {
    if (argc < 3) {
        printf("Usage: %s {%s|%s} <path> [comment]\n", argv[0],RSA, ED25519);
        return EINVAL;
    }

    enum ssh_keytypes_e key_type;
    char* pub_key[1];
    char* key_path = argv[2];

    int key_size = 0;
    ssh_key* pkey = malloc(sizeof(ssh_key));
    char* password = calloc(sizeof(char), MAX_PASSWORD_LENGTH);

    if (strncmp(argv[1], ED25519, strlen(ED25519)) == 0) {
        key_type = SSH_KEYTYPE_ED25519;
        key_size = 4096;
    } else if (strncmp(argv[1], RSA, strlen(RSA)) == 0) {
        key_type = SSH_KEYTYPE_RSA;
    } else {
        return EINVAL;
    }

    fprintf(stderr, "SSH Password> ");
    password = fgets(password, MAX_PASSWORD_LENGTH, stdin);

    for (int i = 0; i < MAX_PASSWORD_LENGTH; i++) {
        if (password[i] == '\n') {
            password[i] = '\0';
        }
    }

    if (password == NULL
            || strlen(password) < MIN_PASSWORD_LENGTH) {
        printf("Invalid password\n");
        return EIO;
    }

    ssh_pki_generate(key_type, 4096, pkey);
    ssh_pki_export_privkey_file(*pkey, password, NULL, NULL, key_path);

    chmod(key_path, S_IRUSR | S_IWUSR); 

    ssh_pki_export_pubkey_base64(*pkey, pub_key);

    const char* str_key_type = ssh_key_type_to_char(key_type);

    if (argc < 4) {
        printf("%s %s\n", str_key_type,
                pub_key[0]);
    } else {
        printf("%s %s %s\n", str_key_type,
                pub_key[0],
                argv[3]);
    }
}
