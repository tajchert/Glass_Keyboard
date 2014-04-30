Google Glass Keyboard
============
App for Google Glass that allows you to input digits and letters, simple as that. Select them by head movement, and tap to make a choice.

###Details
In ```Tools``` class you will find ```inputLength``` that defines how many symbols should user select before terminating Activity. Also in ```Tools``` there is ```saved``` parameter which is String of symbols so far selected.
As well you can there change rows content, order or add new symbols - modify ```rows``` array of Strings.


Selecting digit, one that is upsized is going to be selected when user taps Glasses. On the top there is ```TextView``` that shows digits selected so far (```X``` is for empty selection).

![select symbol](https://raw.githubusercontent.com/tajchert/Glass_Keyboard/master/screenshots/one.jpg)

![select symbol](https://raw.githubusercontent.com/tajchert/Glass_Keyboard/master/screenshots/two.jpg)


###Final note
There is many possibilities to make this software better, but I wrote this as a fun with borrowed Google Glass'es, so I'm not being able to do so. I encourage you to fork, add new features and create pull request.
