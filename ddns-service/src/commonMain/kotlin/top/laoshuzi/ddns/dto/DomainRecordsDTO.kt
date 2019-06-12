package top.laoshuzi.ddns.dto

/**
 * Created by mouse on 2019/6/7.
 */
class AddRecordDTO {
    var domainName: String? = null
    var type: String? = null
    var rR: String? = null
    var value: String? = null
}

class DeleteRecordDTO {
    var recordId: String? = null
}

class UpdateRecordDTO {
    var recordId: String? = null
    var type: String? = null
    var rR: String? = null
    var value: String? = null
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
}
