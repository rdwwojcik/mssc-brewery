package guru.springframework.msscbrewery.web.mappers;

import guru.springframework.msscbrewery.domain.Customer;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.mapstruct.Mapper;

/**
 * Created by radek on 2023-06-09
 */
@Mapper
public interface CustomerMapper {

    CustomerDto customerToCustomerDto(Customer customer);

    Customer customerDtoToCustomer(CustomerDto customerDto);
}
