<template>
  <div class="wrapper">
    <Location>学生信息详情</Location>
    <div class="con">
      <Card :padding="10">
        <div class="info-con">
          <Row>
            <Col span="6">
              <p class="info-photo"><img :src="'/erm/api/ermFile/img/' + studentInfo.photoUrl" onerror="this.src = 'http://iph.href.lu/150x180?text=暂无图片&fg=cccccc&bg=ffffff'"></p>
            </Col>
            <Col span="6">
              <h3>学生基本信息</h3>
              <p class="info-text"><span>姓名：</span><span>{{studentInfo.name}}</span></p>
              <p class="info-text"><span>年级：</span><span>{{studentInfo.grade}}</span></p>
              <p class="info-text"><span>民族：</span><span>{{studentInfo.nature}}</span></p>
              <p class="info-text"><span>监护人姓名：</span><span>{{studentInfo.parentName}}</span></p>
            </CoL>
            <Col span="6">
              <h4>&nbsp;</h4>
              <p class="info-text"><span>学号：</span><span>{{studentInfo.stuno}}</span></p>
              <p class="info-text"><span>班级：</span><span>{{studentInfo.clazz}}</span></p>
              <p class="info-text"><span>政治面貌：</span><span>{{studentInfo.politicalStatus}}</span></p>
              <p class="info-text"><span>监护人电话：</span><span>{{studentInfo.telphone}}</span></p>
            </CoL>
            <Col span="6">
              <h4>&nbsp;</h4>
              <p class="info-text"><span>身份证号：</span><span>{{studentInfo.idcard}}</span></p>
              <p class="info-text"><span>学期：</span><span>{{studentInfo.semester}}</span></p>
              <p class="info-text"><span>毕业学校：</span><span>{{studentInfo.schoolName}}</span></p>
              <p class="info-text"><span>家庭住址：</span><span>{{studentInfo.address}}</span></p>
            </CoL>
          </Row>
        </div>
        <div class="fund-con">
          <h3>贫困信息</h3>
          <p v-for="(item, $index) in poorList"><i>{{$index + 1}}.</i> <span>{{item.key}}：</span><span>{{item.value}}</span></p>
          <p>贫困类型：<span>{{studentInfo.isPoor}}</span></p>
        </div>
        <div class="fund-con">
          <h3>申请表信息</h3>

          <div class="apply">
            <h4>湖南省 补助申请表</h4>
            <div class="mm-table">
              <table border="1">
                <tbody>
                  <tr>
                    <td class="col-1">学生姓名</td>
                    <td>{{studentInfo.name}}</td>
                    <td>性别</td>
                    <td>{{studentInfo.gender == 1 ? '男' : '女'}}</td>
                    <td>出生年月</td>
                    <td>{{studentInfo.birth}}</td>
                    <td>民族</td>
                    <td>{{studentInfo.nature}}</td>
                  </tr>
                  <tr>
                    <td class="col-1">学籍号</td>
                    <td colspan="3">{{studentInfo.stuno}}</td>
                    <td>年级</td>
                    <td>{{studentInfo.grade}}</td>
                    <td>班级</td>
                    <td>{{studentInfo.clazz}}</td>
                  </tr>
                  <tr>
                    <td class="col-1">家庭住址</td>
                    <td colspan="5">{{studentInfo.address}}</td>
                    <td>是否建档立卡户</td>
                    <td >{{applyInfo.isPoor}}</td>
                  </tr>
                  <tr>
                    <td class="col-1">建档立卡人姓名</td>
                    <td colspan="3">{{studentInfo.archiveName}}</td>
                    <td>建档立卡人身份证</td>
                    <td colspan="3">{{studentInfo.archiveIdcard}}</td>
                  </tr>
                  <tr>
                    <td class="col-1">建档立卡人关系</td>
                    <td colspan="3">{{studentInfo.archiveRelation}}</td>
                    <td>建档立卡人账号</td>
                    <td colspan="3">{{studentInfo.archiveAcount}}</td>
                  </tr>
                  <tr>
                    <td class="col-1">学生资助卡姓名</td>
                    <td colspan="3">{{studentInfo.supportName}}</td>
                    <td>学生资助卡账号</td>
                    <td colspan="3">{{studentInfo.supportBankCard}}</td>
                  </tr>
                  <tr>
                    <td class="col-1">家长（监护人）姓名</td>
                    <td>关系</td>

                    <td colspan="6">工作单位及职业</td>
                  </tr>
                  <tr>
                    <td class="col-1">{{studentInfo.parentName}}</td>
                    <td>{{studentInfo.archiveRelation}}</td>

                    <td colspan="6"></td>
                  </tr>
                  <tr>
                    <td class="col-1">{{applyInfo.father}}</td>
                    <td>{{applyInfo.fatherAblity}}</td>

                    <td colspan="6">{{applyInfo.fatherJob}}</td>
                  </tr>
                  <tr>
                    <td class="col-1">{{applyInfo.mother}}</td>
                    <td>{{applyInfo.motherAblity}}</td>

                    <td colspan="6">{{applyInfo.motherJob}}</td>
                  </tr>
                  <tr>
                    <td class="col-1">家庭经济情况（家庭人口、收入及来源等）</td>
                    <td colspan="7">{{applyInfo.familyIncome}}</td>
                  </tr>
                  <tr>
                    <td class="col-1">申请资助理由</td>
                    <td colspan="7">{{applyInfo.applyReason}}</td>
                  </tr>
                  <tr>
                    <td class="col-1">班级评议意见</td>
                    <td colspan="7">
                      <div>
                        <p>同意</p>
                        <p style="text-align: right;">班主任签名：{{studentInfo.headTeacheName}}</p>
                        <p style="text-align: right;">{{studentInfo.operDate}}</p>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td class="col-1">学校审核意见</td>
                    <td colspan="7">
                      <div class="qian">
                        <p>同意</p>
                        <p style="text-align: right;">{{studentInfo.schDate}}</p>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
      <Button type="primary" @click="gotoShenqinEdit()">编辑申请表</Button>

        </div>
        <div class="fund-con">
          <h3>认定状态</h3>
          <p v-for="(item, $index) in confirm"><i>{{$index + 1}}.</i> <span>{{item}}</span></p>
        </div>
        <div class="info-img-con">
          <h3>相关图片</h3>
          <Row :gutter="10">
            <Col span="12">
              <p class="info-img-text"><span>证明材料照片</span></p>
              <p class="info-img-m"><a v-for="item in zmurl" :href="'/erm/api/ermFile/img/' + item" data-lightbox="images"><img :src="'/erm/api/ermFile/img/' + item" onerror="this.src = 'http://iph.href.lu/150x150?text=暂无图片&fg=cccccc&bg=ffffff'"></a></p>
            </CoL>
            <Col span="12">
              <p class="info-img-text"><span>公示照片</span></p>
              <p class="info-img"><a :href="'/erm/api/ermFile/img/' + publicImg" data-lightbox="images"><img :src="'/erm/api/ermFile/img/' + publicImg" onerror="this.src = 'http://iph.href.lu/550x300?text=暂无图片&fg=cccccc&bg=ffffff'"></a></p>
            </CoL>
          </Row>
        </div>

        <v-table v-if="listData.length" :nowrap="true" :items="listData" :header="headData">
          <template slot="d-header" slot-scope="props">
            <th style="width: 150px;" :class="{left:props.idx == 0,center:props.idx > 0}"><p :sort-key="props.data.colCode"><span>{{props.data.colName}}</span><span class="sort"><i class="iconfont up">&#xe6f4;</i><i class="iconfont down">&#xe608;</i></span></p></th>
          </template>
          <template slot="t-body" slot-scope="props">
            <tr>
              <td :class="{left:$index == 0,center:$index > 0}" v-for="(el, $index) in headData" :key="$index"><span>{{props.data[el.colCode]}}</span></td>
            </tr>
          </template>
        </v-table>

        <div class="page clearfix" v-if="total > 10">
          <div class="f-fr">
            <Page :total="total" show-total @on-change="pageChange"></Page>
          </div>
        </div>

        <Nodata v-if="!listData.length"></Nodata>
      </Card>
    </div>
  </div>
</template>

<script>
import Location from 'js/module/components/location/location'
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'
import vTable from 'js/module/components/table/table'
import Nodata from 'js/module/components/nodata/nodata'
import $ from 'jquery'
import lightbox from 'lightbox'

export default {
  name: 'student',
  data () {
    return {
      stuId: '',
      fundId: '',
      zmurl: [],
      publicImg: '',
      seal: '',
      studentInfo: {},
      poorList: [],
      applyInfo: {},
      confirm: [],
      headData: [],
      listData: [],
      total: 0,
      page: {
        page: 1,
        limit: 10
      },
      imgInfo: []
    }
  },
  components: { Location, vTable, Nodata },
  methods: {
    // 获取受助信息
    getStuInfo () {
      io.get(api.student.stuInfo, {
        stuId: this.stuId,
        schFundId: this.fundId
      }, (res) => {
        let data = res.data.stuInfo

        this.studentInfo = data.baseInfo
        this.poorList = data.fundInfo
        this.applyInfo = data.applyInfo
        this.confirm = data.confirmStr.reverse()
        this.seal = data.sealUrl
        this.publicImg = data.publicImg
        this.zmurl = data.applyInfo.evidenceUrls.split(',')
      })
    },
    // 获取照片
    getImg () {
      io.get(api.report.stuImg, {
        studentId: this.stuId,
        schFundedId: this.fundId
      }, (res) => {
        let data = res.data
        this.imgInfo = data.info
      })
    },
    gotoShenqinEdit(){
      this.$router.push({ name: 'file/shenqinEdit', params: { stuId: this.studentInfo.id, schId:this.studentInfo.schoolId }})
    },
    // 获取列表信息
    getList () {
      io.get(api.fundedinfo.studentFund, {
        studentId: this.stuId
      }, (res) => {
        let data = res.data
        this.headData = data.header
        this.listData = data.page.list
        this.total = data.page.totalCount
      })
    },
    // 分页操作
    pageChange (page) {
      this.page.page = page
      this.getList()
    }
  },
  mounted () {
    this.stuId = this.$route.params.stuId || ''
    this.fundId = this.$route.params.fundId || ''
    this.getStuInfo()
    this.getList()
    this.getImg()
    lightbox.option({
      'resizeDuration': 200,
      'wrapAround': true,
      'showImageNumberLabel': false,
      'fadeDuration': 300,
      'imageFadeDuration': 300
    })
  }
}
</script>

<style lang="scss" scoped>
@import "../../../../module/components/main";

.wrapper {
  margin: 0 10px 20px 10px;
  .con {
    .info-con {
      border-bottom: 1px dashed #d5d5d5;
      .info-photo {
        padding-left: 30px;
        img {
          width: 150px;
          height: 180px;
          border: 1px solid #d5d5d5;
        }
      }
      h3 {
        margin-top:5px;
        margin-bottom: 15px;
        color: $color-primary-blue-active;
      }
      h4 {
        margin-top:5px;
        margin-bottom: 15px;
      }
      .info-text {
        height: 35px;
        line-height: 35px;
      }
    }
    .info-img-con {
      border-bottom: 1px dashed #d5d5d5;
      margin-bottom: 10px;
      h3 {
        margin-top:10px;
        margin-bottom: 10px;
        color: $color-primary-blue-active;
      }
      .info-img img {
        width: 100%;
        height: 300px;
        border: 1px solid #d5d5d5;
      }
      .info-img-m img {
        width: 33%;
        height: 150px;
        border: 1px solid #d5d5d5;
      }
    }
    .fund-con {
      border-bottom: 1px dashed #d5d5d5;
      margin-bottom: 10px;
      h3 {
        margin-top:10px;
        margin-bottom: 10px;
        color: $color-primary-blue-active;
      }
      p {
        margin-top: 10px;
        margin-bottom: 10px;
      }
    }
    .apply {
      width: 750px;
      h4 {
        font-size: 16px;
        text-align: center;
        margin: 0 auto;
      }
      .mm-table {
        width: 750px;
        margin-top: 20px;
        margin-bottom: 10px;
        table {
          width: 100%;
          font-size: 13px;
          td {
            min-height: 35px;
            padding: 10px;
          }
          .col-1 {
            width: 100px;
          }
          .qian {
            position: relative;
            .seal {
              position: absolute;
              right: 100px;
              top:-24px;
              img {
                width: 80px;
                height: 80px;
              }
            }
          }
        }
      }
    }
  }
}
</style>
