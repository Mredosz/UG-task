package pl.mateusz.redosz.nbp.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mateusz.redosz.nbp.model.ComputerMapper;
import pl.mateusz.redosz.nbp.model.dto.InvoiceXmlDto;
import pl.mateusz.redosz.nbp.repository.ComputerRepository;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class XmlService {
    private final ComputerRepository computerRepository;
    private final XmlMapper xmlMapper;

    public void saveToXml(){
        try {
            var computers = computerRepository.findAll()
                    .stream()
                    .map(ComputerMapper::toXmlDto)
                    .toList();
            var invoice = new InvoiceXmlDto(computers);

            xmlMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File("faktura.xml"), invoice);
        }catch (IOException e){
            throw new IllegalStateException("Cannot save data to XML file", e);
        }
    }
}
