package com.compay.course;

import com.compay.course.entity.User;
import com.compay.course.entity.UserDao;


public class Main {

    public static void main(String[] args) {
// project structure => libraries => from maven =>  org.mariadb.jdbc:mariadb-java-client

//Create new user create()
        //neues Objekt Klasse User
        User user = new User();
        //daten füllen
        user.setUserName("Martina");
        user.setEmail("martina@11gmail.com");
        user.setPassword("password");
        //neues Objekt Klasse Dao
        UserDao userDao1 = new UserDao();
        //daten von neuen Benutzer in Databank speichern
        //userDao1.create(user);


//Print eine gewählte Person read()
        //Richtig eingesetzte id
        User user2 = userDao1.read(3);
        System.out.println("ID: " + user2.getId() + " USERNAME: " + user2.getUserName() + " EMAIL: " + user2.getEmail() +
                " PASSWORD: " + user2.getPassword());

        //Falsch gesetzte id die nicht in Datenbank gibt
        UserDao userDao2 = new UserDao();
        User user3 = userDao2.read(5);
        System.out.println(user3);

//Update eine gewählte Person update()

        User user4 = userDao1.read(4) ;
        user4.setEmail("karolina11@gmail.com");
        user4.setUserName("Karolina");
        user4.setPassword("password");
        UserDao userDao3 = new UserDao();
       // userDao3.update(user4);


// Delete methode delete()
        UserDao userDao4 = new UserDao();
       // User user5 = userDao4.delete(2);

        User[] allObjectList = new User[] {user,user2,user3};


// Add to array
    User all[] = userDao3.addToArray(user4,allObjectList);

    for(User item: all) {
        System.out.println(item.getUserName());
    }



    }
}
