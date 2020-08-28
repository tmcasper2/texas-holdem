# texas-holdem

Thanks for taking the time to check this out and giving me the opportunity to attend your course! 

My reasoning and thinking process throughout the exercise was basically to first read in the input and organize the board cards separate from the hand cards.  I used a simple case class for the Card and Hand objects, both containing tuples instead of traditional fields because I thought they would be simpler to manipulate throughout the exercise.  

For the `Card(value: (Int, Char, Char))`, I chose to model them with an Int index for easier sorting and comparison, and use Chars for the labels and suits, which seemed fairly obvious.  Each `Hand(value: (Card, Card))` was chosen to contain a Tuple2 of Cards.

For the processing of the hands and board cards I tried to take a simple, sequential approach, staying functional as possible, though my functional skills are a little subpar at times and I did the best I could in spots.  I first processed the board cards, filling in an Array[Card] for them, and then processing the hands as Array[Hand].  

Next I decided to use a workhorse process function to create the handsMap, which is a for/yield expression creating an Array[(Hand, Int)].  The Int that each hand points to is a constant representing the hand rank according to the key at the top of the file.

Inside that process function, as the main 'decider' of each hand rank/type, I tried to make use of the `groupBy` function on the ordered List of 7 cards for each hand. What I realized was that for each hand of 7 cards, they can only be grouped in a finite and manageable set of ways that allowed for a fairly neat and methodical checking of each hand.  I am sure there are better more elegant ways to do this but I started to run out of time and had to settle on this.

After the handsMap is created upon exiting that for/yield expression, I used a simple loop to format the output as necessary.

## Some Shortcomings
I realized toward the end when time was running out for me that I had neglected to implement a ranking of hands with identical values, so for example, an Ac2s3h4d5c vs a higher ranked 3c4h5d6s7c.  So the output will not reflect that when there are testcases with equivalent hand ranks.

Overall, this was a pretty challenging exercise for me.  I am sure that I did not get everything perfect so there will be some testcases that fail, but this is about the best I could push out given the time constraints.  With more time, I'd probably create a more robust and automated testing suite.  For now I just created a bunch of test inputs that I used to manually copy into the input when running.

I will probably also continue to try and refine and optimize it even if I don't get to attend your since it was a fun little challenge and something I can build upon a little to work on my chops.  

## To Run

This is just a standard scala commandline application/executable jar.
To run, assuming you are familiar with SBT, simply clone the repo and `cd` into the root directory:
`texas-holdem/`

Run 
`sbt package`

Run the application:
`scala target/scala-2.13/texas-holdem_2.13-0.1.jar`

Paste or type in your input:
`4cKs4h8s7s Ad4s Ac4d As9s KhKd 5d6d`

Observe the result
`Ac4d=Ad4s 5d6d As9s KhKd`
