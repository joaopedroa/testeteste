package br.com.itau.geradornotafiscal.entrypoint.resources;

import br.com.itau.geradornotafiscal.core.model.NotaFiscal;
import br.com.itau.geradornotafiscal.core.usecases.GeradorNotaFiscalUseCase;
import br.com.itau.geradornotafiscal.entrypoint.domains.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedido")
@RequiredArgsConstructor
public class GeradorNFResource {

	private final GeradorNotaFiscalUseCase notaFiscalUseCase;

	@PostMapping("/gerarNotaFiscal")
	public ResponseEntity<NotaFiscal> gerarNotaFiscal(@RequestBody Pedido pedido) {
		NotaFiscal notaFiscal = notaFiscalUseCase.gerarNotaFiscal(pedido);
		return new ResponseEntity<>(notaFiscal, HttpStatus.OK);
	}
	
}
