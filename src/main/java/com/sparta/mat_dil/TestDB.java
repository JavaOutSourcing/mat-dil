//package com.sparta.mat_dil;
//
//import com.sparta.mat_dil.entity.Follow;
//import com.sparta.mat_dil.entity.User;
//import jakarta.annotation.PostConstruct;
//import jakarta.persistence.EntityManager;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@Component
//@RequiredArgsConstructor
//public class TestDB {
//
//    private final InitService initService;
//
//
//    @PostConstruct
//    public void init() {
//        initService.dbInit1();
//    }
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitService {
//
//        private final EntityManager em;
//
//        @Autowired
//        PasswordEncoder passwordEncoder;
//        //더미 데이터
//        public void dbInit1() {
//            User user = new User();
//            Follow follow = new Follow();
//
//            save(user);
//        }
//
//
//        public void save(Object... objects) {
//            for (Object object : objects) {
//                em.persist(object);
//            }
//        }
//    }
//}
