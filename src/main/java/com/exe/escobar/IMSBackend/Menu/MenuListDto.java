package com.exe.escobar.IMSBackend.Menu;

import com.exe.escobar.IMSBackend.Menu.Menu;
import com.exe.escobar.IMSBackend.Menu.MenuDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MenuListDto {

    List<MenuDto> menuListDto;
}
