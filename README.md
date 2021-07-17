# rule-language

This pseudo-language is based on the sql idea to generate modification to buildings in the Battle Open World game. The base syntax starts with 8 neighbors (will be adjusted to be the number you want in the future), named N0 to N7, you can take a range of neighbors by doing Na-b which will include both of them. An expression will look something like this "Na-b|c-d:Data0-1|Data1-2", the first part is before the ':', it contains N followed by ranges separated by '|', a range can also be a single number. The second part is the bias of each kind of string, this means that if the neighbor 0 is in one of the range and has for string Data1, it will gives one point to the final value and if it is Data1 2 points. If a neighbor appears two times in ranges, it will receive twice the value it should normally receive.

## SWITCH Syntax

A SWITCH object in the syntax will evaluate the following expression and calculate its value, that will be used in the following cases.

For an example, we can reuse the expression "Na-b|c-d:Data0-1|Data1-2"

We get this expression "SWITCH N0-2|5-7:String-1|Object-2"

This will allow you to place cases after giving the outputs of the system

## CASE Syntax

After a SWITCH, you can add as much "CASE" as you want, it must be indented by one space before and after, after it it should contain a number or a range, the values allowed for the number calculated in the switch and then, it needs either to have a DISABLED to tell to return a null string or " : " and then the string you want to return. Warning, in a SWITCH, the disabled are evaluated before the normal so be sure you have no glitches with this 
