package org.usfirst.frc.team6193.robot.commands;

import org.usfirst.frc.team6193.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousCommandGroup extends CommandGroup {

	private int m_autoIndex = 0;

    public AutonomousCommandGroup() {
    	    	
    }
    
    public void selectAutonomousCommandGroup() {
    	switch(m_autoIndex) {
    	case 0:
    		//addSequential(new DrivelineDriveSDCommand(48, 0.1, 2),5);
    		
    		break;
    	case 1:
    		addSequential(new DrivelineDriveSDCommand(48, 0.1, 2),10);
    		break;
    	case 2:
    		addSequential(new DrivelineDriveSDCommand(48, 0.2, 2),10);
    		break;
    	case 3:
    		addSequential(new DrivelineDriveSDCommand(48, 0.3, 2),10);
    		break;
    	case 4:
    		addSequential(new DrivelineDriveSDCommand(48, 0.4, 2),10);
    		break;
    	case 5:
    		addSequential(new DrivelineDriveSDCommand(48, 0.5, 2),10);
    		break;
    	case 6:
    		addSequential(new DrivelineDriveSDCommand(48, 0.6, 2),10);
    		break;
    	case 7:
    		addSequential(new DrivelineDriveSDCommand(48, 0.7, 2),10);
    		break;
    	case 8:
    		addSequential(new DrivelineDriveSDCommand(48, 0.8, 2),10);
    		break;
    	case 9:
    		addSequential(new DrivelineDriveSDCommand(48, 0.9, 2),10);
    		break;
    	case 10:
    		addSequential(new DrivelineDriveSDCommand(48, 1.0, 2),10);
    		break;
    	}
    }
    public int getAutoIndex() {
    	return m_autoIndex;
    }
    public void setAutonomousIndex() {
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
    	m_autoIndex = index;

    }
}
