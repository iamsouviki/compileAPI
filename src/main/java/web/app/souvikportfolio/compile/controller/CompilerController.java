package web.app.souvikportfolio.compile.controller;


import web.app.souvikportfolio.compile.model.CompileandTest;
import web.app.souvikportfolio.compile.model.CompilerModel;
import web.app.souvikportfolio.compile.service.CompileServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class CompilerController {

    @Autowired
    CompileServices compileServices;

    @PostMapping(value = "/compile")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> compileCode(@RequestBody CompilerModel compilerModel) throws IOException {
        try{
            String output = compileServices.compileCode(compilerModel);
            return new ResponseEntity<>(output, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/compile/test")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> compileAndTestCode(@RequestBody CompileandTest compileandTest) throws IOException {
        CompilerModel compilerModel = new CompilerModel();
            compilerModel.setLanguage(compileandTest.getLanguage());
            compilerModel.setCodeText(compileandTest.getCodeText());
            String output = compileServices.compileCode(compilerModel);
            if(compileandTest.getExpectedOutput().equals(output)){
                return new ResponseEntity<>("passed", HttpStatus.OK);
            }else{
               return new ResponseEntity<>("failed", HttpStatus.NOT_ACCEPTABLE);
            }
    }

     @PostMapping(value = "/test")
    @CrossOrigin(origins = "*")
    public Integer compileAndTestCode(@RequestBody Integer compileandTest) throws IOException {
        return compileandTest;
    }
}
