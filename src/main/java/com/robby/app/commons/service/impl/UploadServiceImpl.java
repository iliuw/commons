package com.robby.app.commons.service.impl;

import com.google.common.base.Joiner;
import com.robby.app.commons.pojo.UploadDto;
import com.robby.app.commons.properties.upload.UploadProperties;
import com.robby.app.commons.service.UploadService;
import com.robby.app.commons.utils.CommonUtil;
import com.robby.app.commons.utils.DateTimeUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created @ 2019/11/12
 *
 * @author liuwei
 */
@Service
public class UploadServiceImpl implements UploadService {
    final UploadProperties properties;

    public UploadServiceImpl(UploadProperties properties) {
        this.properties = properties;
    }

    @Override
    public List<UploadDto> multiUpload(List<UploadDto> upFiles) throws IOException {
        Joiner joiner = Joiner.on(File.separator).skipNulls();
        List<UploadDto> result = new ArrayList<>();
        // 初始化文件保存目录，按年月生成文件夹保存
        String desFolder = DateTimeUtil.format(DateTimeUtil.getNow(), "yyyyMM");
        String savePath = joiner.join(properties.getSaveFolder(), desFolder);
        File destination = new File(desFolder);
        if(!destination.exists() && !destination.mkdirs()) {
            throw new IOException("创建目录失败了!");
        }
        // Stream保存文件
        upFiles.parallelStream().forEach(upfile -> {
            String desName = properties.getUseOriginalName() ? upfile.getOriginalName() : CommonUtil.getUUID(upfile.getOriginalName());
            upfile.setDestinationName(desName);
            upfile.setDirectory(desFolder);
            String desPath = joiner.join(savePath, desName);

            // 初始化存储路径
            File desFile = new File(String.format("%s.%s", desPath, upfile.getExtension()));
            // 存储文件到本地
            try {
                FileUtils.copyInputStreamToFile(upfile.getResource().getInputStream(), desFile);
                upfile.setFileSize(desFile.length());
                upfile.setResource(null);
                result.add(upfile);
            } catch (IOException e) {}
        });

        return result;
    }
}
