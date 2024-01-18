package lk.ijse.dep11.app.controller;

import lk.ijse.dep11.app.dto.ResponseDTO;
import lk.ijse.dep11.app.dto.StudentDTO;
import lk.ijse.dep11.app.service.StudentService;
import lk.ijse.dep11.app.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ResponseDTO responseDTO;

    @PostMapping
    public ResponseEntity saveStudent(@RequestBody StudentDTO studentDTO){
        try {
            String res = studentService.saveStudent(studentDTO);
            if(res.equals("00")){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(studentDTO);
                return new ResponseEntity(responseDTO, HttpStatus.CREATED);
            }else if (res.equals("06")){
                responseDTO.setCode((VarList.RSP_DUPLICATED));
                responseDTO.setMessage("Duplicated");
                responseDTO.setContent(studentDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }else {
                responseDTO.setCode((VarList.RSP_FAIL));
                responseDTO.setMessage("failed, Error");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDTO.setCode((VarList.RSP_ERROR));
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}