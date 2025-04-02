package cc.yiueil.controller;

import cc.yiueil.dto.GiftCodeDTO;
import cc.yiueil.dto.UserPickGiftCodeDTO;
import cc.yiueil.service.GiftCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "APP-兑换码")
@RestController
@RequestMapping(value = "/gift_code")
public class GiftCodeController implements BaseController{
    @Autowired
    GiftCodeService giftCodeService;

    @Operation(description = "创建兑换码")
    @PostMapping
    public String createGiftCode(@RequestBody GiftCodeDTO giftCodeDTO) {
        GiftCodeDTO createdGiftCode = giftCodeService.createGiftCode(giftCodeDTO);
        return success(createdGiftCode);
    }

    @Operation(description = "根据 GUID 获取兑换码")
    @GetMapping("/{guid}")
    public String getGiftCode(@PathVariable String guid) {
        GiftCodeDTO giftCode = giftCodeService.getGiftCodeByGuid(guid);
        return success(giftCode);
    }

    @Operation(description = "更新兑换码")
    @PutMapping("/{guid}")
    public String updateGiftCode(@PathVariable String guid, @RequestBody GiftCodeDTO giftCodeDTO) {
        GiftCodeDTO updatedGiftCode = giftCodeService.updateGiftCode(guid, giftCodeDTO);
        return success(updatedGiftCode);
    }

    @Operation(description = "删除兑换码")
    @DeleteMapping("/{guid}")
    public String deleteGiftCode(@PathVariable String guid) {
        giftCodeService.deleteGiftCode(guid);
        return success();
    }

    @Operation(description = "用户领取兑换码")
    @PostMapping("/{guid}/claim")
    public String claimGiftCode(@PathVariable String guid, @RequestParam String userGuid) {
        UserPickGiftCodeDTO userPickGiftCodeDTO = giftCodeService.claimGiftCode(guid, userGuid);
        return success(userPickGiftCodeDTO);
    }

    @Operation(description = "获取所有兑换码")
    @GetMapping
    public String getAllGiftCodes() {
        List<GiftCodeDTO> giftCodes = giftCodeService.getAllGiftCodes();
        return success(giftCodes);
    }
}
