This file is dedicated for the additions.
Each run of the simulation uses one addition.
Usually they are created mid simulation run. However, it takes a great toll over the simulation run time.
When trying to find the mean value for a influencer group, you should aim for a large amount of addition files for a more proper outcome.
You can create more addition files using the python and batch script. Notice: it takes a great deal of time per each iteration.

Do you wish to create more additions csv files?
If you are using the system in the file, do the following:
1. Change the location in the python file to match the desired file location.
2. Change the batch file so it will iteritate through the requested numbers:
  for /l %%x in (*starting number*, 1, *final number*) do
3.run the batch file from cmd
Note: the java system only goes through addition files ending in with consecutive numbers, starting from 1.
That means, if you were to create addition csv files, you need to change the ending so they would be consecutive.

Sharing is caring! if you are to create addition files, it would be appreciated if you were to share them.
