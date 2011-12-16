import csv
import json
import sys

if __name__ == '__main__':
	sent = dict()
	recv = dict()

	data = csv.reader(open(sys.argv[1] + '.final', 'rb'), delimiter=',')
	for row in data:
		concurrency = int(row[0])
		rate = float(row[1])
		mean = float(row[4])

		if concurrency not in sent:
			sent[concurrency] = []
			recv[concurrency] = []

		sent[concurrency].append([rate, mean])
		recv[concurrency].append([concurrency*rate, mean])

	# Sort keys
	for row in sent:
		sent[row].sort(key=lambda p: p[0])
	for row in recv:
		recv[row].sort(key=lambda p: p[0])

	# Flatten dict
	sentarr = [[x,y] for x,y in sent.iteritems()]
	recvarr = [[x,y] for x,y in recv.iteritems()]

	# Sort items
	sentarr.sort(key=lambda p: p[0])
	recvarr.sort(key=lambda p: p[0])

	file(sys.argv[1] + '_sent.json', 'wb').write(json.dumps(sentarr, separators=[',',':']))
	file(sys.argv[1] + '_recv.json', 'wb').write(json.dumps(recvarr, separators=[',',':']))
