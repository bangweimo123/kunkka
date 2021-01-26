package com.leshiguang.arch.kunkka.web.utils;


import com.leshiguang.arch.kunkka.common.entity.category.CategoryBO;
import com.leshiguang.arch.kunkka.common.entity.cluster.ClusterAuthBO;
import com.leshiguang.arch.kunkka.common.entity.connect.ConnectBO;
import com.leshiguang.arch.kunkka.db.entity.gen.*;
import com.leshiguang.arch.kunkka.web.domain.category.CategoryVO;
import com.leshiguang.arch.kunkka.web.domain.cluster.ClusterAuthVO;
import com.leshiguang.arch.kunkka.web.domain.cluster.ClusterVO;
import com.leshiguang.arch.kunkka.web.domain.connect.ConnectVO;

import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-02 20:28
 * @Description
 */
public class Transfer {
    public interface BaseTransfer<PO, BO, VO> {
        VO BO2VO(BO core);

        PO BO2PO(BO core);

        VO PO2VO(PO core);

        PO VO2PO(VO core);

        BO PO2BO(PO core);

        List<VO> BO2VO(List<BO> cores);

        List<VO> PO2VO(List<PO> cores);
    }

    public interface ConnectTransfer extends BaseTransfer<Connect, ConnectBO, ConnectVO> {
    }

    public interface ClusterTransfer {
        ClusterVO PO2VO(Cluster core, List<ClusterRegionMapping> regionMappings);

        Cluster VO2PO(ClusterVO core);
    }

    public interface ClusterAuthTransfer {
        ClusterAuthVO PO2VO(ClusterAuthMapping core);

        ClusterAuthMapping VO2PO(ClusterAuthVO core);

        ClusterAuthBO PO2BO(ClusterAuthMapping core);

        List<ClusterAuthBO> PO2BO(List<ClusterAuthMapping> cores);

        List<ClusterAuthVO> PO2VO(List<ClusterAuthMapping> cores);
    }

    public interface CategoryTransfer extends BaseTransfer<Category, CategoryBO, CategoryVO> {
    }
}
