package org.usfirst.frc.team6193.robot.lib;

import edu.wpi.first.wpilibj.SpeedController;
import com.ctre.phoenix.MotorControl.CAN.TalonSRX;
public class TalonSRX_CAN  extends TalonSRX implements SpeedController {

	public TalonSRX_CAN(int deviceNumber) {
		super(deviceNumber);
	}



}
