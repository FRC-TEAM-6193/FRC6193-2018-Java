package org.usfirst.frc.team6193.robot.commands;

import org.usfirst.frc.team6193.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousCommandGroup extends CommandGroup {

	/*
	 * Comments:
	 * 1. The addSequential methods we are using contains the timeout to cancel the command.
	 * 2. This timeout is a GUESS that needs to be more accurate.
	 * 3. How to make this more accurate
	 *    1. Run the command a few times and create a interpolated tables that calculate the time based on distance and angle.
	 *    	a. This is different data than our old inches per second table.
	 *    2. Make a guess and hope for the best.
	 */
    public AutonomousCommandGroup() {
    	

    	
    }
    
    public void selectAutonomousCommandGroup() {
    	switch(getAutonomousIndex()) {
    	case 0:
    		addSequential(new DrivelineDrivePIDCommand(100, 5, 1),2.4);
    		
    		break;
    	case 1:
    		
    		addSequential(new DrivelineDriveSDCommand(100, 0.5, 2),2);
    		addSequential(new DrivelineDelayCommand(0.25));
    		
    		
    		break;
    	}
    }
    
    public int getAutonomousIndex() {
    	int index = 0;
    	if(Robot.DIO_1.get()) {
    		index = 0x01;
    	}
    	if(Robot.DIO_2.get()) {
    		index = index | 0x02;
    	}
    	if(Robot.DIO_3.get()) {
    		index = index | 0x04;
    	}
    	if(Robot.DIO_4.get()) {
    		index = index | 0x08;
    	}
    	return index;
    }
}
