# FRC6193-2018-Java
Code for expected robot for FRC 2018 Season "First Power Up"
Robot consists of:
1. 2 Dual speed gearboxes driving a 3 wheel drop center robot with 4 inch wheels
2. A pneumatics double solenoid used to shift both gearboxes.
3. 4 CANTalon SRX controllers controlling 4 CIM motors in the driveline gearboxes.

Code consists of:
1. 2018 base Java code from Beta test. <b>Code is untested on RoboRIO</b>
2. Driveline code that controls a arcade style drive with a XBOX controller.
3. Automatic and Manual mode of shifting Hi and Low gears.
4. Autonomous mode selection using 4 digital input switches. 
5. PID modes for driveline in Rotate (Gyro) and Move (Encoder)
6. A start at vision processing. This is <b>unfinished</b>
