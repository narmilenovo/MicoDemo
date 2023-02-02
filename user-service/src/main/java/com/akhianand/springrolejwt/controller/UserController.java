package com.akhianand.springrolejwt.controller;

import com.akhianand.springrolejwt.dao.UserDao;
import com.akhianand.springrolejwt.model.User;
import com.akhianand.springrolejwt.model.UserDto;
import com.akhianand.springrolejwt.model.UserRoleDto;
import com.akhianand.springrolejwt.service.impl.UserExcelExporter;
import com.akhianand.springrolejwt.service.impl.UserService;
import com.akhianand.springrolejwt.vo.ResponseTemplateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @PreAuthorize("hasAuthority('UM_R')")
    @RequestMapping(value="/getAllusers", method = RequestMethod.GET)
    public List<User> listUser(){
        return userService.findAllUser();
    }

    @PreAuthorize("hasAuthority('UM_R')")
    @RequestMapping(value="/getAllUserRoleName", method = RequestMethod.GET)
    public List<UserRoleDto> getAllUserRoleName(){
        return userService.getAllUserRoleName();
    }
    @PreAuthorize("hasAuthority('UM_R')")
    @GetMapping("/userDepartmentWithId/{id}")
    public ResponseTemplateVo getUserDepartmentWithId(@PathVariable("id") Long id) {
        return userService.getUserWithDepartment(id);
    }

    @PreAuthorize("hasAuthority('UM_R')")
    @RequestMapping(value = "/userDepartmentWithEmail/{email}", method = RequestMethod.GET)
    public ResponseTemplateVo getUserDepartmentWithEmail(@PathVariable(value = "email") String email){
        return userService.getUserEmail(email);
    }

    @PreAuthorize("hasAuthority('UM_C')")
    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public User saveUser(@Valid @RequestBody UserDto user){
        return userService.save(user);
    }

    @PreAuthorize("hasAuthority('UM_C')")
    @PostMapping("/bulkSave")
    public List<User> bulkSave(@Valid @RequestBody List<UserDto> userDtoList)
    {
       return userService.bulkSave(userDtoList);
    }

    @PreAuthorize("hasAuthority('UM_D')")
    @DeleteMapping("/deleteById/{id}")
    public String deleteId(@PathVariable Long id) {
        return userService.deleteUserId(id);
    }
    @PreAuthorize("hasAuthority('UM_D')")
    @DeleteMapping("/deleteByBatch/{id}")
    public void deleteBatch(@PathVariable List<Long> id) {
          userService.deleteBatch(id);
    }

//    @RequestMapping(value = "/forgetPassword", method = RequestMethod.GET)
//    public String getPass(@RequestParam String email)
//    {
//        User user=userDao.findByEmail(email);
//        String password=user.getPassword();
//        userService.senttextemail(email,password);
//        return password;
//    }

    @PreAuthorize("hasAuthority('UM_U')")
    @PutMapping("/updateByEmail/{email}")
    public User updateUser(@PathVariable String email,@RequestParam  String firstName,@RequestParam  String lastName, String phone,@RequestParam  String buisness,@RequestParam  String departName,@RequestParam  String plant,@RequestParam  String plantCode,@RequestParam  String[] roles,@RequestParam  boolean status)
    {
        return userService.updateUser(email,firstName,lastName,phone,buisness,departName,plant,plantCode,roles,status);
    }

    @PreAuthorize("hasAuthority('UM_U')")
    @PutMapping("/updateStatusByEmail/{email}")
    public User updateUserStatusEmail(@PathVariable String email, @RequestParam  boolean status)
    {
        return userService.updateStatusUsingEmail(email,status);
    }
    @PreAuthorize("hasAuthority('UM_U')")
    @PutMapping("/updateStatusById/{id}")
    public User updateUserStatusId(@PathVariable Long id, @RequestParam  boolean status)
    {
        return userService.updateStatusUsingId(id,status);
    }

    @PreAuthorize("hasAuthority('UM_U')")
    @PutMapping("/updateBulkStatusUsingId/{id}")
    public List<User> updateBulkStatusUsingId(@PathVariable List<Long> id)
    {
        return userService.updateBulkStatusUsingId(id);
    }

//    @PreAuthorize("hasAuthority('UM_R')")
    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<User> listUsers = userService.findAllUser();

        UserExcelExporter excelExporter = new UserExcelExporter(listUsers);

        excelExporter.export(response);
    }

}
