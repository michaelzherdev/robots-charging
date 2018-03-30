# Robot charging

Simulate the process of charging robots.

### Conditions
6 robots sit at a round table with 6 sockets opposite each.

To charge itself, it must use a charger that can
assembled from two parts: plug and cable.

One part lies on the left of each robot, the second is on the right. Nearby seated robots
compete for the ownership of these parts.

Robots take each of the parts of the charger independently, first one hand, and then the second.

Robots sit at the table with 50% charge. The charger adds 10% charge for
500 ms. We will assume that robots are charged only by intervals of multiples of 500 ms.

Every second robots spend 10% of the charge on some operation.

Robots can have three strategies:

1. random - if not full charge, waiting for the availability of 2 parts of the device,
Charges by 10% and falls asleep at 100300ms (randomly) and repeats;

2. greedy - if the charge is less than 60%, captures 2 parts of the charger and
Charges up to 100%, and if more, it checks the condition every 500 ms;

3. gentlemanly - if the charge of one of the neighbors is less than that of him, then he will give way
charger (falls asleep at 200ms).

### Additional Terms

The robot can not charge more than 100%. If the charge drops to 0 the robot
is turned off.
Consider that robots sit down at the same time and actions occur in real
time.

Simulation can be completed if all robots are fully charged or
turned off. A situation is possible, for example, when 3 robots are turned off and 3 are charged
completely.

The program receives 6 parameters for the input of 6 numbers, indicating the robot's strategy: 1/2/3
respectively.

The program should log changes in robot states (charge level and number of parts of the charger).

