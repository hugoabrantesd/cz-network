package br.edu.fafic.cz_network.controller;


import br.edu.fafic.cz_network.model.Categoria;
import br.edu.fafic.cz_network.model.Pagina;

import br.edu.fafic.cz_network.service.PaginaServiceIMP;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.UUID;

@RestController
@RequestMapping("/pagina")
public class PaginaControler {

    private final PaginaServiceIMP paginaServiceIMP;
   // private final CategoriaServiceIMP categoriaServiceIMP;

    public PaginaControler(PaginaServiceIMP paginaServiceIMP) {
        this.paginaServiceIMP = paginaServiceIMP;
        //this.categoriaServiceIMP = categoriaServiceIMP;
    }
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody Pagina pagina, Categoria categoria){
        return ResponseEntity.ok().body(paginaServiceIMP.save(pagina,categoria));
    }
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Pagina pagina){
        paginaServiceIMP.delete(pagina);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/update")
    public ResponseEntity update(@RequestBody Pagina pagina){
        return ResponseEntity.ok().body(paginaServiceIMP.update(pagina));
    }
    @GetMapping("/get/{id}")
    public ResponseEntity search(@PathVariable UUID id){
        return ResponseEntity.ok().body(paginaServiceIMP.findById(id));
    }
    @GetMapping("/get-all")
    public ResponseEntity all(){
        return ResponseEntity.ok().body(paginaServiceIMP.listar());
    }
//    @PostMapping("/categoria/save")
//    public ResponseEntity save(@RequestBody Categoria categoria){
//        return ResponseEntity.ok().body(categoriaServiceIMP.save(categoria));
//    }
//    @PatchMapping("/categoria/update")
//    public ResponseEntity update(@RequestBody Categoria categoria){
//        return ResponseEntity.ok().body(categoriaServiceIMP.update(categoria));
//    }
    @DeleteMapping("/categoria/deletar")
    public ResponseEntity deletar(@RequestBody String idPagina){
        paginaServiceIMP.deletarCategoria(UUID.fromString(idPagina));
        return ResponseEntity.ok().body(" ");
    }


}
