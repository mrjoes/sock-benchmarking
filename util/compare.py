import json
import sys

if __name__ == '__main__':
	rates = [100, 500, 1000, 2000]

	result = dict()

	for p in sys.argv[1:]:
		data = json.loads(open(p + '_recv.json', 'rb').read())

		flattened = dict()
		for d in data:
			flattened[d[0]] = d[1]

		for r in rates:
			# Find closest rate
			result['%s %d' % (p, r)] = flattened[r]

	# Flatten list
	final = [[x,y] for x,y in result.iteritems()]

	final.sort(key=lambda p: p[0])

	file('summary.json', 'wb').write(json.dumps(final, separators=[',',':']))
