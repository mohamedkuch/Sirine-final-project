package com.example.demo.registration;

import com.example.demo.user.Users;
import com.example.demo.user.UserRepository;
import com.example.demo.admin.Admin;
import com.example.demo.admin.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class  RegistrationService implements Predicate<String> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    public List<Admin> getAllAdmins(){
        return adminRepository.findAll();
    }

    //testing profilpicture
    public Users getUserByID(Long userID){
        return userRepository.findById(userID).orElse(null);
    }

    public boolean createNewAdmin(Admin admin){
        List<Admin> adminList = adminRepository.findAll();
        //prüft, ob es sich um eine reale Mailadresse handelt
        if(test(admin.getEmail())) {


            for (int i = 0; i < adminList.size(); i++) {
                //Prüft ob Email bereits existiert (unique?)
                if (adminList.get(i).getEmail().equals(admin.getEmail())) {
                    return false;
                }
            }

            //TODO: Check der Daten Fehlt noch !!
            this.adminRepository.save(admin);
            return true;
        }
        return false;
    }
    public boolean createNewUser(Users user){
        List<Users> usersList = userRepository.findAll();

        //prüft, ob es sich um eine reale Mailadresse handelt
        if(test(user.getEmail())) {

            for (int i = 0; i < usersList.size(); i++) {
                //Prüft ob EMail bereits existiert (unique?)
                if (usersList.get(i).getEmail().equals(user.getEmail())) {
                    return false;
                }

            }
            //TODO: Check der Daten Fehlt noch !!
            this.userRepository.save(user);
            return true;
        }
        return false;
    }


    //TODO: speichert aus irgendeinem Grund die Accounts nicht, weil immer false returned wird.
    @Override
    public boolean test(String email) {
        return true;
        //return Pattern.compile("^(.+)@(\\S+)$").matcher(email).matches();
    }
}
