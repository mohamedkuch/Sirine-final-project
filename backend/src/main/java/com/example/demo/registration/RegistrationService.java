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

    public boolean createNewAdmin(Admin admin){
        List<Admin> adminList = adminRepository.findAll();

        //Prüft ob Email bereits existiert (unique?)

        for (int i = 0; i < adminList.size(); i++) {

            if (adminList.get(i).getEmail().equals(admin.getEmail())) {
                return false;
            }
        }

        //TODO: Check der Daten Fehlt noch !!
        this.adminRepository.save(admin);
        return true;
    }
    public boolean createNewUser(Users user){
        List<Users> usersList = userRepository.findAll();

        //Prüft ob EMail bereits existiert (unique?)

        for (int i = 0; i < usersList.size(); i++) {

            if (usersList.get(i).getEmail().equals(user.getEmail())) {
                return false;
            }
        }
        //TODO: Check der Daten Fehlt noch !!
        this.userRepository.save(user);
        return true;
    }

    @Override
    public boolean test(String email) {
        return Pattern.compile("^(.+)@(\\S+)$").matcher(email).matches();
    }
}
