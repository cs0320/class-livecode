DB_CONN_STRING="mongodb+srv://default:<password>@cluster92873.2nywbnb.mongodb.net"
DB_NAME="sample_restaurants"
RESTAURANTS_COLLECTION_NAME="restaurants"
NEIGHBORHOODS_COLLECTION_NAME="neighborhoods"

-----------

* I started from: https://www.mongodb.com/compatibility/using-typescript-with-mongodb-tutorial  (https://github.com/mongodb-developer/mongodb-typescript-example)

* I needed to add my (public-facing) IP whenever I started the mongo console. (This broke when I switched to working from home, because only the IP I first created the database from was auto-added.)

* I wasn't able to use the "Games" DB. Instead, I just used the default demo DB, and changed fields. You can find the shape of the collections in the web interface. 

* I followed the template from the guide to access restaurant info. Notice the interface I added for `Grade`. 

* After getting the app running, I had some access problems (the error `user is not allowed to do action [find] on [sample_restaurants.restaurants]`). This is because I wasn't authenticating in the .env file's DB_CONN_STRING. Rather than using the admin user, I created a new "default" user with read/write permissions in the Atlas web interface, and then added "default:<password>" to the connection string as specified in the guide.

* I haven't tested the routes that aren't "GET", but see comments.
