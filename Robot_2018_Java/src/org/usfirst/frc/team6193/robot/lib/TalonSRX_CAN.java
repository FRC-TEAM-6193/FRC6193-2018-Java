package org.usfirst.frc.team6193.robot.lib;

import edu.wpi.first.wpilibj.SpeedController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
public class TalonSRX_CAN  extends TalonSRX implements SpeedController {

	public TalonSRX_CAN(int deviceNumber) {
		super(deviceNumber);
	}

	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(double speed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double get() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopMotor() {
		// TODO Auto-generated method stub
		
	}



}
