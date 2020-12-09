package org.geekbang.homework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geekbang.homework.entity.Address;
import org.geekbang.homework.mapper.AddressMapper;
import org.geekbang.homework.service.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

}
