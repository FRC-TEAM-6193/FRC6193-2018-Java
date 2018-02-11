package org.usfirst.frc.team6193.robot.subsystems;

import org.usfirst.frc.team6193.robot.Robot;
import org.usfirst.frc.team6193.robot.RobotMap;
import org.usfirst.frc.team6193.robot.commands.LiftDefaultCommand;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LiftSubsystem extends Subsystem {
	private WPI_TalonSRX m_LiftLeftMotCtrl;
	private WPI_TalonSRX m_LiftRightMotCtrl;
	public LiftSubsystem(){
		m_LiftLeftMotCtrl = new WPI_TalonSRX(RobotMap.k_LiftLeftMotCtrl_CANID);
		m_LiftRightMotCtrl = new WPI_TalonSRX(RobotMap.k_LiftRightMotCtrl_CANID);
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.


    
    public void raise(double move) {
    	
    	if(!Robot.liftDownLimitSwitch_DIO.get()) { // Down limit
    		if(move < 0.0) {
    			move = 0.0;
    		}
    	}else if(!Robot.liftUpLimitSwitch_DIO.get()) {
    		if(move > 0.0) {
    			move = 0.0;
    		}
    	}
    	// Attempt at limiting the current.
    	// Lifting a 100lb weight took 10 amps total. This will need to be adjusted when we start the climbing tests.
    	if(Math.abs(getCurrent()) > 10) {
    		move = 0.0;
    	}
    	m_LiftLeftMotCtrl.set(move);
    	m_LiftRightMotCtrl.set(-move);
    }
    private double getCurrent() {
    	return m_LiftLeftMotCtrl.getOutputCurrent() + m_LiftRightMotCtrl.getOutputCurrent();
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new LiftDefaultCommand());
    }
}

