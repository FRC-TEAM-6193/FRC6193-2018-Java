package org.usfirst.frc.team6193.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousCommandGroup extends CommandGroup {


	// Constructor
    public AutonomousCommandGroup() {

    }
    /**
     * 
     * @param gameData "xxx" if no data received, otherwise "RRR","LLL","RLR","LRL"
     * @param Side The side of the field the robot was put on during setup. false=Left, true=Right
     * @param Switch Alliance agreement to go for Switch no mater what
     * @param Scale Alliance agreement to go for Scale no mater what
     */
    public void selectAutonomousPlay(String gameData, boolean Side, boolean Switch, boolean Scale) {
    	int play = 0;
//		char swChar = gameData.charAt(0); // Get side of switch
//		char scChar = gameData.charAt(1); // Get side of scale
//
//    	if (gameData == "xxx") { // Cross the line if no good data
//    		play = 0;
//    	} else if (Switch) { // Go for switch
//    		if (Side) { // Robot is on the Right
//    			if (swChar == 'R') { // Switch is on Right
//    				play = 5;
//    			} else { // Switch is on the Left
//    				play = 7;
//    			}
//    		} else { // Robot is on the Left
//    			if ( swChar == 'L') { // Switch is on Left
//    				play = 3;
//    			} else { // switch is on the right
//    				play = 1;
//    			}
//    		}
//    	} else if (Scale){ // Go for scale
//    		if (Side) { // Robot is on the Right
//    			if (scChar == 'R') { // Scale is on Right
//    				play = 6;
//    			} else { //Scale is on the Left
//    				play = 8;
//    			}
//    			
//    		} else {  // Robot is on the Left
//    			if (scChar == 'L') { // Scale is on the Left
//    				play = 4;
//    			} else { // Scale is on the Right
//    				play = 2;
//    			}
//    		}
//    	} else { // Get the first thing on your side of the field, otherwise just cross the line
//    		if (Side) { // Robot is on the Right
//    			if (swChar == 'R') { // Go for switch
//    				play = 5;
//    			} else if (scChar == 'R') { // Go for scale
//    				play = 6;
//    			} else { // Cross the line
//    				play = 0;
//    			}
//    		} else { // Robot is on the Left
//    			if (swChar == 'L') { // Go for switch
//    				play = 3;
//    			} else if (scChar == 'L') { // Go for scale
//    				play = 4;
//    			} else { // Cross the line
//    				play = 0;
//    			}
//    		}
//    	}
    	// Choose the play from the logic above.
    	switch (play) {
    	case 0: // Cross the line
    		addSequential(new DrivelineDriveSDCommand(112, 0.5, 2),10);
    		break; 
    	case 1: // Left position right switch
    		addSequential(new DrivelineDriveSDCommand(127, 0.5, 2),10);
    		addSequential(new DrivelineRotateSDCommand(90, 0.5, 2),10);
    		addSequential(new Lift1Command(0, 0.5, 2),10);
    		addSequential(new Lift2Command(0, 0.5, 2),10);
    		addSequential(new DrivelineDriveSDCommand(3, 0.5, 2),10);
    		addSequential(new CubeCommand(0, 0.5, 2),10);
    		addSequential(new DrivelineDriveSDCommand(3, -0.5, 2),10);
    		addSequential(new Lift1Command(0, -0.5, 2),10);
    		addSequential(new Lift2Command(0, -0.5, 2),10);
    		break;
    	case 2: // Left position right scale
    		addSequential(new DrivelineDriveSDCommand(233, 0.5, 2),10);
    		addSequential(new DrivelineRotateSDCommand(90, 0.5, 2),10);
    		addSequential(new DrivelineDriveSDCommand(250, 0.5, 2),10);
    		addSequential(new DrivelineRotateSDCommand(90, -0.5, 2),10);
    		addSequential(new DrivelineDriveSDCommand(70, 0.5, 2),10);
    		addSequential(new DrivelineRotateSDCommand(90, -0.5, 2),10);
    		addSequential(new Lift1Command(0, 0.5, 2),10);
    		addSequential(new Lift2Command(0, 0.5, 2),10);
    		addSequential(new DrivelineDriveSDCommand(5, 0.5, 2),10);
    		addSequential(new CubeCommand(0, 0.5, 2),10);
    		addSequential(new DrivelineDriveSDCommand(5, -0.5, 2),10);
    		addSequential(new Lift1Command(0, -0.5, 2),10);
    		addSequential(new Lift2Command(0, -0.5, 2),10);
    		break;
    	case 3: // Left position left switch
    		addSequential(new DrivelineDriveSDCommand(127, 0.5, 2),10);
    		addSequential(new DrivelineRotateSDCommand(90, 0.5, 2),10);
    		addSequential(new Lift1Command(0, 0.5, 2),10);
    		addSequential(new Lift2Command(0, 0.5, 2),10);
    		addSequential(new DrivelineDriveSDCommand(3, 0.5, 2),10);
    		addSequential(new CubeCommand(0, 0.5, 2),10);
    		addSequential(new DrivelineDriveSDCommand(3, -0.5, 2),10);
    		addSequential(new Lift1Command(0, -0.5, 2),10);
    		addSequential(new Lift2Command(0, -0.5, 2),10);
    		break;
    	case 4: // Left position left scale
    		addSequential(new DrivelineDriveSDCommand(324, 0.5, 2),10);
    		addSequential(new DrivelineRotateSDCommand(90,0.5,2),10);
    		addSequential(new DrivelineDriveSDCommand(7, 0.5, 2),10);
    		addSequential(new Lift1Command(0, 0.5, 2),10);
    		addSequential(new Lift2Command(0, 0.5, 2),10);
    		addSequential(new CubeCommand(0, 0.5, 2),10);
    		addSequential(new DrivelineDriveSDCommand(7, -.5, 2),10);
    		addSequential(new Lift1Command(0, -0.5, 2),10);
    		addSequential(new Lift2Command(0, -0.5, 2),10);
    		
    		break;
    	case 5: // Right position right switch
    		addSequential(new DrivelineDriveSDCommand(144, 0.5, 2),10);
    		addSequential(new DrivelineRotateSDCommand(90, -0.5, 2),10);
    		addSequential(new Lift1Command(0, 0.5, 2),10);
    		addSequential(new Lift2Command(0, 0.5, 2),10);
    		addSequential(new DrivelineDriveSDCommand(15, 0.5, 2),10);
    		addSequential(new CubeCommand(0, 0.5, 2),10);
    		addSequential(new DrivelineDriveSDCommand(17, -0.5, 2),10);
    		addSequential(new Lift1Command(0, -0.5, 2),10);
    		addSequential(new Lift2Command(0, -0.5, 2),10);
    		break;
    	case 6: // Right position right scale
    		addSequential(new DrivelineDriveSDCommand(320, 0.5, 2),10);
    		addSequential(new DrivelineRotateSDCommand(90, -0.5, 2),10);
    		addSequential(new Lift1Command(0, 0.5, 2),10);
    		addSequential(new Lift2Command(0, 0.5, 2),10);
    		addSequential(new DrivelineDriveSDCommand(7, 0.5, 2),10);
    		addSequential(new CubeCommand(0, 0.5, 2),10);
    		addSequential(new DrivelineDriveSDCommand(8, -0.5, 2),10);
    		addSequential(new Lift1Command(0, -0.5, 2),10);
    		addSequential(new Lift2Command(0, -0.5, 2),10);
    		break;
    	case 7: // Right position left switch
    		addSequential(new DrivelineDriveSDCommand(233, 0.5, 2),10);
    		addSequential(new DrivelineRotateSDCommand(90, -0.5, 2),10);
    		addSequential(new DrivelineDriveSDCommand(250, 0.5, 2),10);
    		addSequential(new DrivelineRotateSDCommand(90, 0.5, 2),10);
    		addSequential(new DrivelineDriveSDCommand(70, 0.5, 2),10);
    		addSequential(new DrivelineRotateSDCommand(90, 0.5, 2),10);
    		addSequential(new Lift1Command(0, 0.5, 2),10);
    		addSequential(new Lift2Command(0, 0.5, 2),10);
    		addSequential(new DrivelineDriveSDCommand(3, 0.5,2),10);
    		addSequential(new CubeCommand(0, 0.5, 2),10);
    		addSequential(new DrivelineDriveSDCommand(5, -0.5, 2),10);
    		addSequential(new Lift1Command(0, -0.5, 2),10);
    		addSequential(new Lift2Command(0, -0.5, 2),10);
    		break;
    	case 8: // Right position left scale
    		addSequential(new DrivelineDriveSDCommand(233, 0.5, 2),10);
    		addSequential(new DrivelineRotateSDCommand(90, -0.5, 2),10);
    		addSequential(new DrivelineDriveSDCommand(250, 0.5, 2),10);
    		addSequential(new DrivelineRotateSDCommand(90, 0.5, 2),10);
    		addSequential(new DrivelineDriveSDCommand(70, 0.5, 2),10);
    		addSequential(new Lift1Command(0, 0.5, 2),10);
    		addSequential(new Lift2Command(0, 0.5, 2),10);
    		addSequential(new DrivelineDriveSDCommand(5, 0.5, 2),10);
    		addSequential(new CubeCommand(0, 0.5, 2),10);
    		addSequential(new DrivelineDriveSDCommand(6, -0.5, 2),10);
    		addSequential(new Lift1Command(0, -0.5, 2),10);
    		addSequential(new Lift2Command(0, -0.5, 2),10);
    		break; 
    	}
    	
    }
    
}

