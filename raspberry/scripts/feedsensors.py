import mosquitto
import io
import time
import random
from datetime import datetime

galvbase = 20
start = time.time()

while True:
	measure = random.randint(3, 16) + 60
	heartbeat = '<p style="font-size:24px; color:red"> %i bpm</p>' % (measure)
	fheart = open('/home/pi/www/heartbeat.html', 'w')
	fheart.write(heartbeat)
	fheart.close()

	f = open('/home/pi/www/sensortext.html', 'w')
	clock = datetime.utcnow()
	clockmsg = '<p> %s </p>' % (str(clock))
	f.write(clockmsg)
	if time.time() - start > 15:
		f.write("<p>New objective from mission control incoming:</p>")
	if time.time() - start > 30:
		f.write("<p>Present TetrisSuit for SpaceAppsChallenge...</p>")
	fheader = open('/home/pi/www/headertext.html', 'w')
	if time.time() - start < 10:
		fheader.write('<h3  style="color:blue">CHECKING UPLINK...</h3>')
	if time.time() - start < 30:
		fheader.write('<h3 style="color:green">CONNECTION ESTABLISHED</h3>')
	if time.time() - start < 600:
		fheader.write('<h3 style="color:white">MISSION OPS ONLINE</h3>')
	fheader.close()
	timer = time.time() - start 
	countdown = 120 - timer
	timermsg = '<p>Stage One %i seconds </p>' % (countdown)
	if countdown < 0:
		timermsg = '<p>Please start Stage Two!</p>'
	f.write(timermsg)
	measure2 = random.randint(7, 33) + galvbase
	galvanic = '<p>galvanic = %i units</p>' % (measure2)
	f.write(galvanic)
	f.close()
	time.sleep(0.5)


