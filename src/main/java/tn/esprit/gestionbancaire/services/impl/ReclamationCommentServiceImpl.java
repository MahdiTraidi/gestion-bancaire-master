package tn.esprit.gestionbancaire.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.gestionbancaire.exception.EntityNotFoundException;
import tn.esprit.gestionbancaire.exception.ErrorCodes;
import tn.esprit.gestionbancaire.model.ReclamationComment;
import tn.esprit.gestionbancaire.model.User;
import tn.esprit.gestionbancaire.repository.ReclamationCommentRepository;
import tn.esprit.gestionbancaire.services.ReclamationCommentService;
import tn.esprit.gestionbancaire.services.UserService;
import tn.esprit.gestionbancaire.utils.Utility;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class ReclamationCommentServiceImpl implements ReclamationCommentService {

    private ReclamationCommentRepository reclamationCommentRepository;
    private UserService userService;
    @Autowired
    public ReclamationCommentServiceImpl(ReclamationCommentRepository reclamationCommentRepository,UserService userService) {
        this.reclamationCommentRepository = reclamationCommentRepository;
        this.userService= userService;
    }

    @Override
    public ReclamationComment findById(Integer id) {
        return reclamationCommentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "There is no comment found with ID = " + id,
                        ErrorCodes.RECLAMATION_COMMENT_NOT_FOUND)
        );
    }

    @Override
    public List<ReclamationComment> findAll() {
        return reclamationCommentRepository.findAll();
    }

    @Override
    public ReclamationComment save(ReclamationComment reclamationComment) {
        reclamationComment.setCreationDate(Instant.now());
        reclamationComment.setLastModifiedDate(Instant.now());
        return  reclamationCommentRepository.save(reclamationComment);
    }

    @Override
    public ReclamationComment update(Integer id,ReclamationComment newReclamationComment) {
        ReclamationComment reclamationComment = findById(id);
        reclamationComment.setLastModifiedDate(Instant.now());
        reclamationComment.setComment(newReclamationComment.getComment());
        return  reclamationCommentRepository.save(reclamationComment);
    }

    @Override
    public void delete(Integer id) {
//        ReclamationComment reclamationComment=findById(id);
//        Long id_user = reclamationComment.getUserId();
//        User user = userService.getUserById(id_user);
//        User user = Utility.getCurrenUser();
        reclamationCommentRepository.deleteById(id);

    }
}
