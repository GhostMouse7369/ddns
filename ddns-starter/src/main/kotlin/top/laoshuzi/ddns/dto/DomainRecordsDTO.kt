package top.laoshuzi.ddns.dto

/**
 * Created by mouse on 2019/6/7.
 */
class AddRecordDTO {
    var domainName: String? = null
    var type: String? = null
    var rR: String? = null
    var value: String? = null
    override fun toString(): String {
        return "AddRecordDTO(domainName=$domainName, type=$type, rR=$rR, value=$value)"
    }
}

class DeleteRecordDTO {
    var recordId: String? = null
    override fun toString(): String {
        return "DeleteRecordDTO(recordId=$recordId)"
    }
}

class UpdateRecordDTO {
    var recordId: String? = null
    var type: String? = null
    var rR: String? = null
    var value: String? = null
    override fun toString(): String {
        return "UpdateRecordDTO(recordId=$recordId, type=$type, rR=$rR, value=$value)"
    }
}

class RecordDTO {
    var domainName: String? = null
    var recordId: String? = null
    var rR: String? = null
    var type: String? = null
    var value: String? = null
    var tTL: Long? = null
    var priority: Long? = null
    var line: String? = null
    var status: String? = null
    var locked: Boolean? = null
    var weight: Int? = null
    var remark: String? = null
    override fun toString(): String {
        return "RecordDTO(domainName=$domainName, recordId=$recordId, rR=$rR, type=$type, value=$value, tTL=$tTL, priority=$priority, line=$line, status=$status, locked=$locked, weight=$weight, remark=$remark)"
    }
}
