# timsproject

Notes:

2015-02-27: Did a new commit, although not much has actually changed...

Spent some time trying to figure out the error with uploading image files to
my database. The POST calls work, but the file is being sent as content-size 0. I
haven't figured it out yet, so I ended up putting the code back the way it was.

I also started a new repo called pytest. I added the pydev plugin and set up
a test project to link to my main java project. I'm not completely sure how that works 
yet, but that will be next after the image db issue is resolved.

I found a python lib that does neural network and machine learning stuff, but I'm
having issues with the installation. Will revisit that later.

2015-02-15:The project is set up with basic REST APIs to interact with a "parts" table. 
Assuming there will be part names, model numbers, types, etc., associated with the parts
represented in the images being processed, I created the parts table to hold whatever 
relevant info we will need. For now, it has the three fields listed above (modelNum is 
the primary key).

  The app is built off the music store app's framework, so it has all the same REST
functionality, along with a delete function and the ability to search by all three fields.

All Java files besides main are currently in the test folder--I'm having an issue with adding 
other packages that I have to fix at some point, but for now I don't want it to impede my 
progress any further.

I'm working on adding file uploads and downloads for images now. I'll add changes once that's 
tested.

The app is running on heroku at https://timsproject.herokuapp.com.
