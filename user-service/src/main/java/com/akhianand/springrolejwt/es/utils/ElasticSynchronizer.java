package com.akhianand.springrolejwt.es.utils;

import com.akhianand.springrolejwt.dao.PrivilegeDao;
import com.akhianand.springrolejwt.dao.RoleDao;
import com.akhianand.springrolejwt.dao.UserDao;
import com.akhianand.springrolejwt.es.dao.PrivilegeElkDao;
import com.akhianand.springrolejwt.es.dao.RoleElkDao;
import com.akhianand.springrolejwt.es.dao.UserElkDao;
import com.akhianand.springrolejwt.es.model.PrivilegeElk;
import com.akhianand.springrolejwt.es.model.RoleElk;
import com.akhianand.springrolejwt.es.model.UserElk;
import com.akhianand.springrolejwt.model.Privilege;
import com.akhianand.springrolejwt.model.Role;
import com.akhianand.springrolejwt.model.User;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ElasticSynchronizer {
    @Autowired
    private UserDao userDAO;
    @Autowired
    private RoleDao roleDAO;
    @Autowired
    private PrivilegeDao privilegeDAO;
    @Autowired
    private UserElkDao userESRepo;
    @Autowired
    private RoleElkDao roleESRepo;
    @Autowired
    private PrivilegeElkDao privilegeESRepo;
    @Autowired
    private ModelMapper mapper;


    private static final Logger LOG = LoggerFactory.getLogger(ElasticSynchronizer.class);


    @Scheduled(cron = "*/10 * * * * *")
    @Transactional
    public void sync() {
        LOG.info("Start Syncing - {}", LocalDateTime.now());
        this.syncUsers();
        LOG.info(" End Syncing - {}", LocalDateTime.now());
    }

    private void syncUsers() {

        Specification<User> userSpecification = (root, criteriaQuery, criteriaBuilder) ->
                getModificationDatePredicate(criteriaBuilder, root);
        Specification<Role> roleSpecification = (root, criteriaQuery, criteriaBuilder) ->
                getModificationDatePredicate(criteriaBuilder, root);
        Specification<Privilege> privilegeSpecification = (root, criteriaQuery, criteriaBuilder) ->
                getModificationDatePredicate(criteriaBuilder, root);
        List<User> userList;
        List<Role> roleList;
        List<Privilege> privilegeList;
        if (userESRepo.count() == 0 ) {
            userList = userDAO.findAll();
            roleList = roleDAO.findAll();
            privilegeList = privilegeDAO.findAll();
        } else {
            userList = userDAO.findAll(userSpecification);
            roleList = roleDAO.findAll(roleSpecification);
            privilegeList = privilegeDAO.findAll(privilegeSpecification);
        }
        for(User user: userList) {
            LOG.info("Syncing User - {}", user.getId());
            userESRepo.save(this.mapper.map(user, UserElk.class));
        }
        for(Role role: roleList) {
            LOG.info("Syncing Role - {}", role.getId());
            roleESRepo.save(this.mapper.map(role, RoleElk.class));
        }
        for(Privilege privilege: privilegeList) {
            LOG.info("Syncing Privilege - {}", privilege.getId());
            privilegeESRepo.save(this.mapper.map(privilege, PrivilegeElk.class));
        }
    }

    private static Predicate getModificationDatePredicate(CriteriaBuilder cb, Root<?> root) {
        Expression<Timestamp> currentTime;
        currentTime = cb.currentTimestamp();
        Expression<Timestamp> currentTimeMinus = cb.literal(new Timestamp(System.currentTimeMillis() -
                (180_000)));
        return cb.between(root.<Date>get("updatedAt"),
                currentTimeMinus,
                currentTime
        );
    }

}
