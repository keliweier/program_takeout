package com.service.impl;

import com.context.BaseContext;
import com.entity.AddressBook;
import com.mapper.AddressBookMapper;
import com.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Override
    public List<AddressBook> findAll() {
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        return addressBookMapper.findByCondition(addressBook);
    }

    @Override
    public List<AddressBook> findDefault(AddressBook addressBook) {
        addressBook.setIsDefault(1);
        addressBook.setUserId(BaseContext.getCurrentId());
        return addressBookMapper.findByCondition(addressBook);
    }

    @Override
    public AddressBook findById(Long id) {
        return addressBookMapper.findById(id);
    }

    @Override
    public void saveAddressBook(AddressBook addressBook) {
        addressBook.setIsDefault(0);
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.saveAddressBook(addressBook);
    }

    @Override
    public void updateAddressBook(AddressBook addressBook) {
        addressBookMapper.updateAddressBook(addressBook);
    }

    @Override
    public void updateAddressBookIsDefault(AddressBook addressBook) {
        addressBook.setIsDefault(0);
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.updateAllAddressBookIsDefault(addressBook);

        addressBook.setIsDefault(1);
        addressBookMapper.updateAddressBook(addressBook);
    }

    @Override
    public void deleteAddressBook(Long id) {
        addressBookMapper.deleteAddressBook(id);
    }
}
