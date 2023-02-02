package com.akhianand.springrolejwt.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.akhianand.springrolejwt.config.TokenProvider;
import com.akhianand.springrolejwt.dao.RoleDao;
import com.akhianand.springrolejwt.dao.UserDao;
import com.akhianand.springrolejwt.model.*;
import com.akhianand.springrolejwt.vo.Department;
import com.akhianand.springrolejwt.vo.ResponseTemplateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;


//    @Autowired
//    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private NoOpPasswordEncoder noOpPasswordEncoder;


//    @Autowired
//    private JavaMailSender javaMailSender;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private RestTemplate restTemplate;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email);
        if(user == null ){
            throw new UsernameNotFoundException("Invalid username or password.");

        }
//        else if (user.isStatus() == false) {
//            throw new UsernameNotFoundException("Inactive User");
//
//        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),user.isStatus(),true,true,true, getAuthorities(user.getRoles()));

//
//        return org.springframework.security.core.userdetails.User
//                .withUsername(user.getEmail())
//                .password(user.getPassword())
//                .authorities(getAuthorities(user.getRoles()))
//                .accountExpired(false)
//                .accountLocked(false)
//                .disabled(false)
//                .credentialsExpired(false)
//                .build();
    }

   /*  private Set<SimpleGrantedAuthority> getAuthority(User user) {
          Set<SimpleGrantedAuthority> authorities = new HashSet<>();
          user.getRoles().forEach(role -> {
              authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
          });
          return authorities;

      }*/
   private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
       Set<GrantedAuthority> authorities = new HashSet<>();
       for (Role role: roles) {
           authorities.add(new SimpleGrantedAuthority(role.getName()));
           authorities.addAll(role.getPrivileges()
                   .stream()
                   .map(p -> new SimpleGrantedAuthority(p.getName()))
                   .collect(Collectors.toList()));
       }
       return authorities;
   }

    public ResponseTemplateVo getUserWithDepartment(Long id) {
        ResponseTemplateVo vo = new ResponseTemplateVo();
        User user = userDao.findUserById(id);
        Department department= restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/dept/"+user.getDepartName()
                ,Department.class);
        vo.setUser(user);
        vo.setDepartment(department);
        return vo;
    }


 /*   public User findById(Long id) {
        return userDao.findById(id).get();
    }*/

/*
    public User getUserEmail(String email) {
        return userDao.findByEmail(email);
    }
*/

    public ResponseTemplateVo getUserEmail(String email) {
        ResponseTemplateVo vo = new ResponseTemplateVo();
        User user = userDao.findByEmail(email);
        Department department= restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/dept/"+user.getDepartName()
                ,Department.class);
        vo.setUser(user);
        vo.setDepartment(department);
        return vo;
    }

    public List<User> findAllUser() {
        List<User> list = new ArrayList<>();
        userDao.findAll(Sort.by("id").ascending()).iterator().forEachRemaining(list::add);
        return list;
    }

    public List<UserRoleDto> getAllUserRoleName() {
        return userDao.findAll(Sort.by("id").ascending()).stream().map(this::convertUserRoleDto).collect(Collectors.toList());
    }


    private UserRoleDto convertUserRoleDto (User user)
    {
        UserRoleDto userRoleDto = new UserRoleDto();
        userRoleDto.setId(user.getId());
        userRoleDto.setEmail(user.getEmail());
        userRoleDto.setPhone(user.getPhone());
        userRoleDto.setFirstName(user.getFirstName());
        userRoleDto.setLastName(user.getLastName());
        userRoleDto.setBusiness(user.getBusiness());
        userRoleDto.setDepartName(user.getDepartName());
        userRoleDto.setPlant(user.getPlant());
        userRoleDto.setPlantCode(user.getPlantCode());
        userRoleDto.setStatus(user.isStatus());
        userRoleDto.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
        return userRoleDto;
    }



 /*  *//* public User save(UserDto user) {

        User nUser = user.getUserFromDto();
        nUser.setPassword(bcryptEncoder.encode(user.getPassword()));

        Role role = roleDao.findRoleByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        if(nUser.getEmail().split("@")[1].equals("admin.edu")){
            role = roleDao.findRoleByName("ADMIN");
            roleSet.add(role);
        }

        nUser.setRoles(roleSet);
        return userDao.save(nUser);
    }
*/
    @Valid
   public User save(UserDto user) {
       User newUser = new User();
//       newUser.setEmail(senttext(user.getEmail()));
       newUser.setEmail(user.getEmail());
       newUser.setPassword(noOpPasswordEncoder.encode(user.getPassword()));
//     newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
       newUser.setPhone(user.getPhone());
       newUser.setFirstName(user.getFirstName());
       newUser.setLastName(user.getLastName());
       newUser.setBusiness(user.getBusiness());
       newUser.setDepartName(user.getDepartName());
       newUser.setPlant(user.getPlant());
       newUser.setPlantCode(user.getPlantCode());
       newUser.setRoles(getRoles1(user.getRoles()));;
       newUser.setStatus(user.isStatus());
       return userDao.save(newUser);
   }

   public List<User> bulkSave(List<UserDto> userDtoList)
   {
       List<User> users = new ArrayList<>();
       for (UserDto user : userDtoList)
       {
           User existemail=userDao.findByEmail(user.getEmail());

           if(existemail!=null) {

               throw new IllegalArgumentException("User Already Exists");

           }else
           {
        	 
        	   user.setStatus(false);
//        	   user.setEmail(senttext(user.getEmail()));
        	   user.setEmail(user.getEmail());
               users.add(save(user));
           }
       }
       return users;
   }


  /*  public User update(UserDto user) {
        User existingUser = userDao.findUserById(user.getId());
        existingUser.setFirstName(user.getLastName());
        existingUser.setPhone(user.getPhone());
        existingUser.setLastName(user.getLastName());
        existingUser.setBusiness(user.getBusiness());
        existingUser.setPlant(user.getPlant());
        existingUser.setPlantCode(user.getPlantCode());
//        existingUser.setRoles(getRoles(user.getRoles()));
        existingUser.setStatus(user.isStatus());
        return userDao.save(existingUser);
    }*/
   /* public String updateUser(User user)
    {
        User existingUser = userDao.findUserById(user.getId());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPhone(user.getPhone());
        existingUser.setBusiness(user.getBusiness());
        existingUser.setDepartmentId(user.getDepartmentId());
        existingUser.setPlant(user.getPlant());
        existingUser.setPlantCode(user.getPlantCode());
        existingUser.setRoles(user.getRoles());
        existingUser.setStatus(user.isStatus());
         userDao.save(existingUser);
        return "User updated Successfully";
    }*/
    @Valid
    public User updateUser(String email,String firstName, String lastName, String phone,String buisness,String departName, String plant, String plantCode, String[] roles, boolean status)
    {
        User existingUser = userDao.findByEmail(email);
        existingUser.setFirstName(firstName);
        existingUser.setLastName(lastName);
        existingUser.setPhone(phone);
        existingUser.setBusiness(buisness);
        existingUser.setDepartName(departName);
        existingUser.setPlant(plant);
        existingUser.setPlantCode(plantCode);
        existingUser.setRoles(getRoles1(roles));
        existingUser.setStatus(status);
       return userDao.save(existingUser);
    }

    public User updateStatusUsingEmail(String email,boolean status)
    {
        User existingUser = userDao.findByEmail(email);
        existingUser.setStatus(status);
        return userDao.save(existingUser);
    }
    public User updateStatusUsingId(Long id,boolean status)
    {
        User existingUser = userDao.findUserById(id);
        existingUser.setStatus(status);
        return userDao.save(existingUser);
    }

    public List<User> updateBulkStatusUsingId(List<Long> id)
    {
        List<User> existingUser = userDao.findAllById(id);
        for (User user: existingUser)
        {
            user.setStatus(user.isStatus() == true ? false : true);
        }
        userDao.saveAll(existingUser);
        return existingUser;
    }

    private Set<Role> getRoles1(String[] roles){
        Set<Role> userRoles = new HashSet<>();
        for(String role : roles) {
            userRoles.add(roleDao.findRoleByName(role));
        }
        return userRoles;
    }

    public User validation(String token)
    {
        User user =userDao.findByEmail(tokenProvider.getUsernameFromToken(token));
        if (user == null) {
            throw new UsernameNotFoundException("The user doesn't exist");
        }
        return user;
    }

    public String deleteUserId(Long id) {
        userDao.deleteById(id);
        return "User removed !! " + id;
    }
    
    @Transactional
    public void deleteBatch(List<Long> userIdList){
    	
	        	   List<User> userList = userDao.findAllById(userIdList);
	        	  try {
	        	    userDao.deleteAll(userList);
	        	  }
	        	  catch (Exception e) {
					
				}
    }


//    public void senttextemail(String email,String password){
//        System.out.println(password);
//        Date currentUtilDate = new Date();
//        SimpleMailMessage msg=new SimpleMailMessage();
//        msg.setFrom("codabanulab@gmail.com");
//        msg.setTo(email);
//        msg.setBcc(email);
//        msg.setCc(email);
//        msg.setSentDate(currentUtilDate);
//        msg.setSubject("Your Current Password");
//        msg.setReplyTo("banupriya3195@gmail.com");
//        msg.setText("Your Password is : "+password);
//        javaMailSender.send(msg);
//    }
//    public String senttext(String email){
//        Date currentUtilDate = new Date();
//        SimpleMailMessage msg=new SimpleMailMessage();
//        msg.setFrom("codabanulab@gmail.com");
//        msg.setTo(email);
//        msg.setBcc(email);
//        msg.setCc(email);
//        msg.setSentDate(currentUtilDate);
//        msg.setSubject("Prosol Login Credential");
//        msg.setReplyTo("banupriya3195@gmail.com");
//        msg.setText("Your Account is Created!! ");
//        javaMailSender.send(msg);
//		return email;
//    }



}
