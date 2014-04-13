import io
import time
import mosquitto
import picamera
import mosquitto

def on_connect(mosq, obj, rc):
    if rc == 0:
        print("Connected successfully.")

with picamera.PiCamera() as camera:
	camera.start_preview()
	time.sleep(2)
	print "ready to start"
	camera.stop_preview()

	start = time.time()
	stream = io.BytesIO()

	client = mosquitto.Mosquitto("raspberry-video")
	client.on_connect = on_connect

	client.connect("127.0.0.1")

	for foo in camera.capture_continuous(stream, format='jpeg'):
		stream.seek(0)
		bytes = stream.read()
		print time.time()
		print stream
		#client.publish(topic, payload=None, qos=0, retain=false)
		client.publish("tetris/pics/raw", bytes, 1)
		# bytes = ""
		if time.time() - start > 300:
			client.disconnect()
			break
		time.sleep(0.5)
		stream.seek(0)
		stream.truncate()

