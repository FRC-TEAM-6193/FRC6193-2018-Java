package org.usfirst.frc.team6193.robot.subsystems;

import org.usfirst.frc.team6193.robot.Cals;
import org.usfirst.frc.team6193.robot.RobotMap;
import org.usfirst.frc.team6193.robot.commands.ShooterDefaultCommand;
import org.usfirst.frc.team6193.robot.lib.MotorData;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 */
public class ShooterSubsystem extends Subsystem {

	private TalonSRX m_shooterMotCtrl_1;
	
	private double m_gearboxRatio = 10.71;
	private double m_setPoint = Cals.k_ShooterDefaultVelocity;
	private double m_P_Gain = 0.05;
	private double m_maxSpeed = MotorData.MiniCIM_RPM / m_gearboxRatio;
	private boolean m_isActive = false;
    // Initialize your subsystem here
    public ShooterSubsystem() {
    	m_shooterMotCtrl_1 = new TalonSRX(RobotMap.k_ShooterMotCtrl_1_CANID);
    	
    }
    public boolean isActive() {
    	return m_isActive;
    }
    public void isActive(boolean active) {
    	m_isActive = active;
    }

    public double getSetPoint() {
    	return m_setPoint;
    }
    public void setSetPoint(double val) {
    	m_setPoint = val;
    }
    public double getVelocity() {
    	return m_shooterMotCtrl_1.getSelectedSensorVelocity(0);
    }
    public void setVelocity() {
    	if(m_isActive) {
    		setVelocity(m_setPoint);
    	}else {
    		setVelocity(0.0);
    	}
    }
    public void stepVelocity(double step) {
    	m_setPoint += step;
    }
    public void setVelocity(double velocity) {
    	m_setPoint = velocity;
    	double output = limit(m_setPoint)/m_maxSpeed;
    	output = output + (m_P_Gain * getError());
    	if(!RobotState.isDisabled()) {
    		m_shooterMotCtrl_1.set(ControlMode.PercentOutput, output);
    	}
    }
    
    private double getError() {
    	double error = m_setPoint - getVelocity();
    	error = Math.abs(error) < Cals.k_ShooterErrorDeadband ? 0.0 : error;
    	return error;
    }
    private double limit(double val) {
    	val = val > m_maxSpeed ? m_maxSpeed : val;
    	val = val < 0.0 && val < -m_maxSpeed ? -m_maxSpeed: val; 
    	return val;
    }
    public void initDefaultCommand() {
        setDefaultCommand(new ShooterDefaultCommand());
    }
}
