package spaceapps.challenge.simplegui;

//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

/**
 * Hello world!
 * 
 */
public class App extends JFrame {
	
	public void update(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		System.out.println("try update");

		try {

			String mqttHost = "tcp://172.16.55.197:1883";
			String mqttClientId = "javahud";
			final String mqttTopic = "tetris/pics/raw";

			MQTT mqtt = new MQTT();
			mqtt.setHost(mqttHost);
			mqtt.setClientId(mqttClientId);

			BlockingConnection connection = mqtt.blockingConnection();
			connection.connect();
			System.out.println("connected to channels");
			Topic[] topics = { new Topic(mqttTopic, QoS.AT_LEAST_ONCE) };
			byte[] qoses = connection.subscribe(topics);

				Message message = connection.receive();
				System.out.println(message.getTopic());
				byte[] payload = message.getPayload();
				System.out.println(payload.length);
				ByteArrayInputStream bis = new ByteArrayInputStream(payload);
				javax.imageio.stream.ImageInputStream iis = ImageIO
						.createImageInputStream(bis);
				BufferedImage mqPic = ImageIO.read(iis);

				// process the message then:

				message.ack();
				

				getContentPane().removeAll();
				final JPanel comboPanel = new JPanel();
				getContentPane().add(comboPanel);
				JLabel comboLbl = new JLabel("HUD:");

				comboPanel.add(comboLbl);

				JButton exitButton = new JButton("Exit");

				// The ActionListener class is used to handle the
				// event that happens when the user clicks the button.
				// As there is not a lot that needs to happen we can
				// define an anonymous inner class to make the code simpler.
				exitButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						// When the fruit of veg button is pressed
						System.exit(0);

					}
				});

				// The JFrame uses the BorderLayout layout manager.
				// Put the two JPanels and JButton in different areas.
				add(comboPanel, BorderLayout.NORTH);
				add(exitButton, BorderLayout.SOUTH);

				// make sure the JFrame is visible

				// make sure the program exits when the frame closes
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setTitle("Example HUD GUI");
				setSize(800, 600);
				JLabel mqLabel = new JLabel(new ImageIcon(mqPic));
				comboPanel.add(mqLabel);
				repaint();
				validate();
			
			
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public App() throws IOException {

		final JPanel comboPanel = new JPanel();
		getContentPane().add(comboPanel);
		JLabel comboLbl = new JLabel("HUD:");

		comboPanel.add(comboLbl);

		JButton exitButton = new JButton("Exit");

		// The ActionListener class is used to handle the
		// event that happens when the user clicks the button.
		// As there is not a lot that needs to happen we can
		// define an anonymous inner class to make the code simpler.
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// When the fruit of veg button is pressed
				System.exit(0);

			}
		});

		JButton smtButton = new JButton("update");
		smtButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// When the fruit of veg button is pressed
				update(getGraphics());

			}
		});

		// The JFrame uses the BorderLayout layout manager.
		// Put the two JPanels and JButton in different areas.
		add(comboPanel, BorderLayout.NORTH);
		add(smtButton, BorderLayout.SOUTH);
		add(exitButton);

		

		// make sure the JFrame is visible

		// make sure the program exits when the frame closes
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Example HUD GUI");
		setSize(800, 600);
		BufferedImage myPicture = ImageIO.read(new File(
				"C:\\Users\\akmoch\\Desktop\\default.jpg"));
		JLabel mqLabel = new JLabel(new ImageIcon(myPicture));
		comboPanel.add(mqLabel);
	}

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				System.out.println("NASA Space Apps Challenge");

				try {
					App theApp = new App();
					theApp.setVisible(true);
					

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}
}
