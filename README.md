HOW IT WORKS:

Controllers are connected as bluetooth input devices via standard Android bluetooth devices screen. They can be given a name which will be stored locally and will be used to configure them.
NXT units must be manually connected from the app screen. They will turn green once connected.

Controllers can be configured to control NXTs. From the configuration screen each controller can be assigned to one or more NXT. Select the controller button/stick function, then select the appropriate action for one or more of the motors of the selected NXT (run, stop, coast, nothing).

To associate more than one NXT to the same controller, configure them one by one by selecting them from the list at the bottom of the screen.

After each selection the configuration is stored on the local device.

ISSUES:
- NXT Bluetooth is very picky. It can drop at any time for no reason, leaving the motors in their current state, which might be running. Reconnecting the NXT unit will revert it to a normal state. Turning it off will also stop the motor.
- Disconnecting a controller while the app is running will make it crash. Working on a fix.

NOTES:
- The connection is one way, which means the app is unaware of the current motor status. It will send commands and never wait for confirmation.
