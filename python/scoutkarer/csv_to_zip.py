import os
import csv
import requests

api_key = os.environ.get('GMAPS_API_KEY')
api_url = "https://maps.googleapis.com/maps/api/geocode/json?latlng={},{}&key={}"


def zip_from_result(result):
    try:
        return list(
                filter(lambda component: 'postal_code' in component['types'],
                result['results'][0].get('address_components')))[0]['long_name']
    except:
        return 'XXX XX'

"""
with open('distance_from_skraddartorp.csv') as f:
    csv_lines = csv.reader(f, delimiter=';')
    for line in csv_lines:
        longitude = line[-2]
        latitude = line[-1]
        r = requests.get(api_url.format(longitude, latitude, api_key))
        line.append(postal_code)
        print(';'.join(line))
"""

api_url_address = "https://maps.googleapis.com/maps/api/geocode/json?address={}&key={}"

with open('distance_from_skraddartorp.csv') as f:
    csv_lines = csv.reader(f, delimiter=';')
    for line in csv_lines:
        address = line[1]
        r = requests.get(api_url_address.format(address, api_key))
        zip_code = zip_from_result(r.json())
        line.append(zip_code)
        print(';'.join(line))
