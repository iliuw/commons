package com.robby.app.commons.service;

import com.robby.app.commons.pojo.UploadDto;
import org.springframework.core.io.InputStreamSource;

import java.io.IOException;
import java.util.List;

/**
 * Created @ 2019/11/12
 *
 * @author liuwei
 */
public interface UploadService {

    /**
     * 文件上传，支持批量上传
     * @param upFiles
     * @return
     * @throws IOException
     */
    List<UploadDto> multiUpload(List<UploadDto> upFiles) throws IOException;

}
