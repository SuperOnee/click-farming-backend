package com.fakedonald.clickfarming.service.system

import com.fakedonald.clickfarming.contorller.system.FundChangeRecordQueryRequest
import com.fakedonald.clickfarming.domain.FundChangeRecord
import com.fakedonald.clickfarming.enums.sales.TradeTypeEnum
import com.fakedonald.clickfarming.enums.sales.UserTypeEnum
import com.fakedonald.clickfarming.extension.and
import com.fakedonald.clickfarming.extension.between
import com.fakedonald.clickfarming.extension.equal
import com.fakedonald.clickfarming.extension.generatePageRequest
import com.fakedonald.clickfarming.repository.system.FundChangeRecordRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.math.BigDecimal
import kotlin.random.Random

interface FundChangeRecordService {

    fun saveFundChangeRecord(
        username: String, userType: UserTypeEnum, userId: Long, amount: BigDecimal, remark: String?,
        balance: BigDecimal, adminUsername: String
    )

    fun queryPage(request: FundChangeRecordQueryRequest): Page<FundChangeRecord>
}

@Service
class FundChangeRecordServiceImpl(val fundChangeRecordRepository: FundChangeRecordRepository) :
    FundChangeRecordService {

    /**
     * 保存资金变动
     */
    override fun saveFundChangeRecord(
        username: String, userType: UserTypeEnum, userId: Long, amount: BigDecimal, remark: String?,
        balance: BigDecimal, adminUsername: String
    ) {
        val fundChangeRecord =
            FundChangeRecord(
                transactionNo = "${System.currentTimeMillis()}${Random.nextInt(0, 10000)}",
                userType = userType,
                username = username,
                userId = userId,
                tradeType = if (amount < BigDecimal.ZERO) TradeTypeEnum.CHANGE_MINUS else TradeTypeEnum.CHANGE_PLUS,
                amount = amount,
                remark = remark,
                balance = balance,
                operatedUser = adminUsername,
            )
        fundChangeRecordRepository.save(fundChangeRecord)
    }

    /**
     * 分页查询
     */
    override fun queryPage(request: FundChangeRecordQueryRequest): Page<FundChangeRecord> {
        val pageRequest = generatePageRequest()
        return fundChangeRecordRepository
            .findAll(
                and(
                    FundChangeRecord::userId.equal(request.userId),
                    FundChangeRecord::userType.equal(request.userType),
                    // nullable conditions
                    FundChangeRecord::orderNo.equal(request.orderNo),
                    FundChangeRecord::taskNo.equal(request.taskNo),
                    FundChangeRecord::operatedUser.equal(request.operatedUser),
                    FundChangeRecord::tradeType.equal(request.tradeType),
                    FundChangeRecord::createAt.between(request.startAt, request.endAt)
                ),
                pageRequest.withSort(Sort.by("createAt").descending())
            )
    }

}