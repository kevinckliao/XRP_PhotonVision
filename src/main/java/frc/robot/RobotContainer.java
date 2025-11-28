// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.xrp.XRPGyro;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.AutoForward;
import frc.robot.commands.AutoRotate;
import frc.robot.commands.ForwardUltra;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.Ultrasonic;
import frc.robot.subsystems.XRPDrivetrain;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final XRPDrivetrain m_xrpDrivetrain = new XRPDrivetrain();

  private final XRPGyro m_gyro = new XRPGyro();

  private final Ultrasonic m_ultra = new Ultrasonic();
  
  private final AutoForward m_Forward = new AutoForward(m_xrpDrivetrain);

  private final AutoRotate m_Rotate = new AutoRotate(m_xrpDrivetrain, m_gyro, 90.0);

  private final AutoForward m_Forward2 = new AutoForward(m_xrpDrivetrain);

  private final AutoRotate m_Rotate2 = new AutoRotate(m_xrpDrivetrain, m_gyro, 90.0);

  private final ForwardUltra m_ForwardUltra = new ForwardUltra(m_xrpDrivetrain, m_ultra);

  private final ForwardUltra m_ForwardUltra2 = new ForwardUltra(m_xrpDrivetrain, m_ultra);

  private final CommandXboxController controller = new CommandXboxController(0);

  private final DriveCommand m_driveCommand;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    m_driveCommand = new DriveCommand(m_xrpDrivetrain, ()-> -controller.getLeftX(), ()-> -controller.getLeftY());
    m_xrpDrivetrain.setDefaultCommand(m_driveCommand);
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    // return Commands.sequence(m_Forward, m_Rotate, m_Forward2, m_Rotate2); // do from abc
    return Commands.sequence(m_Forward);
  }
}
