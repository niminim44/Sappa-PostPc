# Sappa-PostPc

This application was built as a course project during my Bs.c. in the Hebrew University. 

BACKGROUND:
An android app for giving away second hand products based on user's current location
The login stage is performed only by logging in to user's facebook account, the reason for that
is lack of time for creating my own user management logics, although if this project were to grow, 
I would definitely create one...

I used google's live database provided by Firebase in order to store the data related to the user's posts,
and in order to store app's images.

This app is built with the standard MVP concept. 

dependencies:

for photos: glide

for login: facebook sdk

for permissions: Rx-permissions

for multithreading : RxJava2

for comfort : Butterknife + eventbus
