package br.com.ibict.web.page;

import com.codahale.metrics.annotation.Timed;
import br.com.ibict.domain.Chart;
import br.com.ibict.web.rest.util.HeaderUtil;
import br.com.ibict.web.rest.util.PaginationUtil;
import br.com.ibict.repository.ChartRepository;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import br.com.ibict.web.rest.errors.*;


/**
 * REST controller for managing Name.
 */
@Controller
@RequestMapping("/visao")
public class Visao {

	private final Logger log = LoggerFactory.getLogger(Visao.class);

	private ChartRepository chartRepository;

    public Visao(ChartRepository chartRepository) {
		this.chartRepository = chartRepository;
    }

    @GetMapping("/{id}")
	@ResponseBody
    public String getAllNames(@PathVariable("id") long id) {
		
		//throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found", null);
		//throw new InternalServerErrorException("No user was found for this reset key");
		
		//log.debug("REST request to get Chart : {}", id);
        Optional<Chart> chart = chartRepository.findById(id);
		
		if (chart.isPresent()) {
            String view =  chart.get().getHtml();
			return view;
        } else {
			return "404";
		}
		
		//chartRepository.getOne(1l);
		//System.out.println(chart.get().getId());
		
    }

    
}
