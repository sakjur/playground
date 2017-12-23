import json
from geopy.distance import vincenty

SKRADDARTORP_COORDINATES = (59.992, 15.328)

to_pos = lambda s: float(s[:6].replace(',', '.'))

with open("scoutkarer.json") as f:
    groups = json.loads(f.read())

groups = list(filter(lambda g: 'lat' in g and 'lon' in g, groups))
for group in groups:
    group['lat'] = to_pos(group['lat'])
    group['lon'] = to_pos(group['lon'])
    group['distance'] = vincenty(SKRADDARTORP_COORDINATES, (group['lat'], group['lon'])).kilometers

groups = sorted(groups, key=lambda g: g['distance'])
groups = filter(lambda g: g['distance'] < 200, groups)
for group in groups:
    print("{};{};{:.1f};{};{}".format(group['Title'], group.get('adr'), group.get('distance'), group.get('lat'), group.get('lon')))
