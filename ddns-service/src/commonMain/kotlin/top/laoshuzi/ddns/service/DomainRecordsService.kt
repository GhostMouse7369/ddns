package top.laoshuzi.ddns.service

import top.laoshuzi.ddns.dto.AddRecordDTO
import top.laoshuzi.ddns.dto.DeleteRecordDTO
import top.laoshuzi.ddns.dto.RecordDTO
import top.laoshuzi.ddns.dto.UpdateRecordDTO

/**
 * Created by mouse on 2019/6/7.
 */
interface DomainRecordsService {

    fun getDomainRecords(domain: String): List<RecordDTO>

    fun getDomainRecordByType(domain: String, type: String): List<RecordDTO>

    fun addDomainRecord(addRecord: AddRecordDTO, ip: String)

    fun deleteDomainRecord(deleteRecord: DeleteRecordDTO)

    fun updateDomainRecord(updateRecord: UpdateRecordDTO)
}
