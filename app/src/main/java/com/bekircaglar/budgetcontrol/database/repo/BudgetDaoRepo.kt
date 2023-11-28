package com.bekircaglar.budgetcontrol.database.repo

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.bekircaglar.budgetcontrol.R
import com.bekircaglar.budgetcontrol.databinding.FragmentBudgetBinding
import com.bekircaglar.budgetcontrol.databinding.FragmentNewExpenseBinding
import com.bekircaglar.budgetcontrol.fragments.details.MorePageFragmentDirections
import com.bekircaglar.budgetcontrol.model.BankIncomeModel
import com.bekircaglar.budgetcontrol.model.BankModel
import com.bekircaglar.budgetcontrol.model.CashIncomeModel
import com.bekircaglar.budgetcontrol.model.CashModel
import com.bekircaglar.budgetcontrol.adapter.BankAdapter
import com.bekircaglar.budgetcontrol.adapter.BankIncomeAdapter
import com.bekircaglar.budgetcontrol.adapter.CashAdapter
import com.bekircaglar.budgetcontrol.adapter.CashIncomeAdapter
import com.bekircaglar.budgetcontrol.databinding.FragmentNewIncomeBinding
import com.bekircaglar.budgetcontrol.model.AccountsMoney
import com.bekircaglar.budgetcontrol.model.UserData
import com.bekircaglar.budgetcontrol.room.AccountsMoneyDao
import com.bekircaglar.budgetcontrol.room.BankexpenseDao
import com.bekircaglar.budgetcontrol.room.BankincomeDao
import com.bekircaglar.budgetcontrol.room.CashexpenseDao
import com.bekircaglar.budgetcontrol.room.CashincomeDao
import com.bekircaglar.budgetcontrol.room.UserDataDao
import com.bekircaglar.budgetcontrol.viewmodel.BudgetFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class BudgetDaoRepo(
    var amdao: AccountsMoneyDao,
    var bedao:BankexpenseDao,
    var bidao:BankincomeDao,
    var cedao:CashexpenseDao,
    var cidao:CashincomeDao,
    var uddao: UserDataDao
){

    var categoryNameIncome =""

    var userDataM:MutableLiveData<List<UserData>>
    var categoryNameM :MutableLiveData<String>
    var expensesListCashM:MutableLiveData<List<CashModel>>
    var expenseListBankM:MutableLiveData<List<BankModel>>
    var AccountsMoneyListM:MutableLiveData<List<AccountsMoney>>
    var incomeListCashM:MutableLiveData<List<CashIncomeModel>>
    var incomeListBankM:MutableLiveData<List<BankIncomeModel>>
    var categoryNameIncomeM:MutableLiveData<String>

    init {
        userDataM = MutableLiveData()
        AccountsMoneyListM = MutableLiveData()
        categoryNameIncomeM = MutableLiveData()
        incomeListCashM = MutableLiveData()
        incomeListBankM = MutableLiveData()
        categoryNameM = MutableLiveData()
        expensesListCashM = MutableLiveData()
        expenseListBankM = MutableLiveData()
    }

    fun bringUserDataList():MutableLiveData<List<UserData>>{
        return userDataM

    }
    fun bringAccountsMoneyList():MutableLiveData<List<AccountsMoney>>{
        return AccountsMoneyListM

    }
    fun bringCagegoryNameM():MutableLiveData<String>{
        return categoryNameM

    }
    fun bringExpenseBankList():MutableLiveData<List<BankModel>>{
        return expenseListBankM

    }
    fun bringExpenseCashList():MutableLiveData<List<CashModel>>{
        return expensesListCashM

    }
    fun bringIncomeBankList():MutableLiveData<List<BankIncomeModel>>{
        return incomeListBankM

    }
    fun bringIncomeCashList():MutableLiveData<List<CashIncomeModel>>{
        return incomeListCashM

    }
    fun bringCategoryIncomeNameM():MutableLiveData<String>{
        return categoryNameIncomeM
    }




    fun expenseButton(viewModel: BudgetFragmentViewModel,binding:FragmentBudgetBinding,context: Context,cashList:ArrayList<CashModel>,bankList:ArrayList<BankModel>){
        binding.incomeTransText.background = null
        binding.incomeTransText.setTextColor(Color.BLACK)
        binding.expenseTransText.setBackgroundResource(R.drawable.shape2)
        binding.expenseTransText.setTextColor(Color.WHITE)

        binding.cashRecycle.adapter = CashAdapter(viewModel,context,cashList)
        binding.bankRectycle.adapter = BankAdapter(viewModel,context,bankList)

        val adaptercashexpense = CashAdapter(viewModel,context,cashList)
        binding.expenseCashAdapter = adaptercashexpense

        val adapterbankexpense = BankAdapter(viewModel,context,bankList)
        binding.expenseBankAdapter = adapterbankexpense

    }

    fun incomeButoon(viewModel: BudgetFragmentViewModel,binding:FragmentBudgetBinding,context: Context,incomeCashList:ArrayList<CashIncomeModel>,incomeBankList:ArrayList<BankIncomeModel>){
        binding.expenseTransText.background = null
        binding.expenseTransText.setTextColor(Color.BLACK)
        binding.incomeTransText.setBackgroundResource(R.drawable.shape1)
        binding.incomeTransText.setTextColor(Color.WHITE)

        binding.cashRecycle.adapter = CashIncomeAdapter(viewModel,context,incomeCashList)
        binding.bankRectycle.adapter = BankIncomeAdapter(viewModel,context,incomeBankList)

        val incomecashadapter = CashIncomeAdapter(viewModel,context,incomeCashList)
        binding.incomeCashAdapter = incomecashadapter

        val incomebankadapter = BankIncomeAdapter(viewModel,context,incomeBankList)
        binding.incomeBankAdapter =incomebankadapter
    }

    fun goProfilePageButton(view: View){
        val actprofile = MorePageFragmentDirections.actionMorePageFragmentToProfileFragment()
        Navigation.findNavController(view).navigate(actprofile)

    }
    fun goHelpPageButton(view: View){
        val acthelp = MorePageFragmentDirections.actionMorePageFragmentToHelpFragment()
        Navigation.findNavController(view).navigate(acthelp)

    }
    fun goAccountButton(view: View){
        val actacc = MorePageFragmentDirections.actionMorePageFragmentToAccountsFragment()
        Navigation.findNavController(view).navigate(actacc)

    }
    fun goDashButton(view: View){
        val actdash = MorePageFragmentDirections.actionMorePageFragmentToDashboardFragment()
        Navigation.findNavController(view).navigate(actdash)

    }
    fun NullAllAccount(binding:FragmentNewExpenseBinding){
        binding.cashAccountButton.setBackgroundResource(R.drawable.cash_nav)
        binding.bankAccountButton.setBackgroundResource(R.drawable.button_navi)

    }
    fun NullAllImg(binding:FragmentNewExpenseBinding){
        binding.imageViewCar.background = null
        binding.imageViewApart.background = null
        binding.imageViewBeauty.background = null
        binding.imageViewClothes.background = null
        binding.imageViewPet.background = null
        binding.imageViewDonate.background = null
        binding.imageViewHealth.background = null
        binding.imageViewFood.background = null
        binding.imageViewGift.background = null
        binding.imageViewOther.background = null

    }
    fun SelectBankAccountButton(binding: FragmentNewExpenseBinding) {
        NullAllAccount(binding)
        binding.bankAccountButton.setBackgroundResource(R.drawable.button_navi_full)

    }

    fun SelectCashAccountButton(binding: FragmentNewExpenseBinding){
        NullAllAccount(binding)
        binding.cashAccountButton.setBackgroundResource(R.drawable.cash_nav_full)

    }
    fun selectCatagory(binding:FragmentNewExpenseBinding){

        var categoryName:String = "Other"

        binding.imageViewApart.setOnClickListener {
            NullAllImg(binding)
            binding.imageViewApart.setBackgroundResource(R.drawable.shape0)
            categoryName = "Apart"
            categoryNameM.value = categoryName
        }
        binding.imageViewBeauty.setOnClickListener {
            NullAllImg(binding)
            binding.imageViewBeauty.setBackgroundResource(R.drawable.shape0)
            categoryName = "Beauty"
            categoryNameM.value = categoryName
        }
        binding.imageViewCar.setOnClickListener {
            NullAllImg(binding)
            binding.imageViewCar.setBackgroundResource(R.drawable.shape0)
            categoryName = "Car"
            categoryNameM.value = categoryName
        }
        binding.imageViewClothes.setOnClickListener {
            NullAllImg(binding)
            binding.imageViewClothes.setBackgroundResource(R.drawable.shape0)
            categoryName = "Clothes"
            categoryNameM.value = categoryName
        }
        binding.imageViewPet.setOnClickListener {
            NullAllImg(binding)
            binding.imageViewPet.setBackgroundResource(R.drawable.shape0)
            categoryName = "Pet"
            categoryNameM.value = categoryName
        }
        binding.imageViewDonate.setOnClickListener {
            NullAllImg(binding)
            binding.imageViewDonate.setBackgroundResource(R.drawable.shape0)
            categoryName = "Donate"
            categoryNameM.value = categoryName
        }
        binding.imageViewHealth.setOnClickListener {
            NullAllImg(binding)
            binding.imageViewHealth.setBackgroundResource(R.drawable.shape0)
            categoryName = "Health"
            categoryNameM.value = categoryName
        }
        binding.imageViewFood.setOnClickListener {
            NullAllImg(binding)
            binding.imageViewFood.setBackgroundResource(R.drawable.shape0)
            categoryName = "Food"
            categoryNameM.value = categoryName
        }
        binding.imageViewGift.setOnClickListener {
            NullAllImg(binding)
            binding.imageViewGift.setBackgroundResource(R.drawable.shape0)
            categoryName = "Gift"
            categoryNameM.value = categoryName

        }
        binding.imageViewOther.setOnClickListener {
            NullAllImg(binding)
            binding.imageViewOther.setBackgroundResource(R.drawable.shape0)
            categoryName = "Other"
            categoryNameM.value = categoryName

        }
    }

    fun getAccountsMoney(){
        val job = CoroutineScope(Dispatchers.Main).launch {
            AccountsMoneyListM.value = amdao.getAccountsMoney()
        }
    }
    fun updateAccountsMoney(newBankMoney:String,newCashMoney:String){
        val job = CoroutineScope(Dispatchers.Main).launch {
            val updatedMoney = AccountsMoney(1,newBankMoney,newCashMoney)
            amdao.updateAccountsMoney(updatedMoney)
        }
    }
    fun getUserData(){
        val job = CoroutineScope(Dispatchers.Main).launch {
            userDataM.value = uddao.getUser()

        }
    }
    fun updateUserData(newUsername:String,newUserEmail:String,newUserPhone:String,newUserImg:String,newUserDob:String) {
        val job = CoroutineScope(Dispatchers.Main).launch {
            val updatedUserData =
                UserData(1, newUsername, newUserEmail, newUserPhone, newUserImg, newUserDob)
            uddao.updateUser(updatedUserData)
        }
    }
    fun getBankexpenseList(){
        val job = CoroutineScope(Dispatchers.Main).launch {
            expenseListBankM.value = bedao.Bankexpenselistdao()
        }
    }
    fun getCashexpenseList(){
        val job = CoroutineScope(Dispatchers.Main).launch {
            expensesListCashM.value = cedao.Cashexpenselistdao()


        }
    }
    fun getBankincomeList(){
        val job = CoroutineScope(Dispatchers.Main).launch {
            incomeListBankM.value = bidao.Bankincomelistdao()
        }
    }
    fun getCashincomeList(){
        val job = CoroutineScope(Dispatchers.Main).launch {
            incomeListCashM.value = cidao.Cashincomelistdao()

        }
    }

    fun addBankexpenseList(exenseImg:Int,expenseDate:String,expensePrice:Int,expenseCategory:String){
        val job = CoroutineScope(Dispatchers.Main).launch {
            val newbankexpense = BankModel(0,exenseImg,expensePrice,expenseCategory,expenseDate)
            bedao.addBankexpenselist(newbankexpense)

        }
    }
    fun addCashexpenseList(exenseImg:Int,expenseDate:String,expensePrice:Int,expenseCategory:String){
        val job = CoroutineScope(Dispatchers.Main).launch {
            val newcashexpense = CashModel(0,exenseImg,expensePrice,expenseCategory,expenseDate)
            cedao.addCashexpenselist(newcashexpense)

        }
    }
    fun addBankincomeList(incomeImg:Int,incomeDate:String,incomeBy:String,incomePrice:Int){
        val job = CoroutineScope(Dispatchers.Main).launch {
            val newbankincome = BankIncomeModel(0,incomeImg,incomeBy,incomeDate,incomePrice)
            bidao.addBankincomelist(newbankincome)

        }
    }
    fun addCasgincomeList(incomeImg:Int,incomeDate:String,incomeBy:String,incomePrice:Int){
        val job = CoroutineScope(Dispatchers.Main).launch {
            val newcashincome = CashIncomeModel(0,incomeImg,incomeBy,incomeDate,incomePrice)
            cidao.addCashincomelist(newcashincome)

        }
    }

    fun deleteBankexpenseList(bankexpense_id:Int){
        val job = CoroutineScope(Dispatchers.Main).launch {
            val newbankexpense = BankModel(bankexpense_id,0,0,"","")
            bedao.deleteBankexpenselist(newbankexpense)
            getBankexpenseList()
            println("as")

        }
    }
    fun deleteCashexpenseList(cashexpense_id:Int){
        val job = CoroutineScope(Dispatchers.Main).launch {
            val newcashexpense = CashModel(cashexpense_id,0,0,"","")
            cedao.deleteCashexpenselist(newcashexpense)
            getCashexpenseList()

        }
    }
    fun deleteBankincomeList(bankincome_id:Int){
        val job = CoroutineScope(Dispatchers.Main).launch {
            val newbankincome = BankIncomeModel(bankincome_id,0,"","",0)
            bidao.deleteBankincomelist(newbankincome)
            getBankincomeList()
        }
    }
    fun deleteCashincomeList(cashincome_id:Int){
        val job = CoroutineScope(Dispatchers.Main).launch {
            val newcashincome = CashIncomeModel(cashincome_id,0,"","",0)
            cidao.deleteCashincomelist(newcashincome)
            getCashincomeList()

        }
    }

    fun nullallcategoryincome(binding: FragmentNewIncomeBinding){
        binding.imageView.background = null
        binding.imageView4.background = null
        binding.imageView5.background = null
        binding.imageView6.background = null

    }
    fun nullaccountall(binding: FragmentNewIncomeBinding){
        binding.cashAccountButton.setBackgroundResource(R.drawable.cash_nav)
        binding.bankAccountButton.setBackgroundResource(R.drawable.button_navi)
    }
    fun selectcategoryincome(binding: FragmentNewIncomeBinding){
        binding.imageView.setOnClickListener {
            nullallcategoryincome(binding)
            binding.imageView.setBackgroundResource(R.drawable.shape0)
            categoryNameIncome = "Salary"
            categoryNameIncomeM.value = categoryNameIncome

        }
        binding.imageView4.setOnClickListener {
            nullallcategoryincome(binding)
            binding.imageView4.setBackgroundResource(R.drawable.shape0)
            categoryNameIncome = "Income"
            categoryNameIncomeM.value = categoryNameIncome

        }
        binding.imageView5.setOnClickListener {
            nullallcategoryincome(binding)
            binding.imageView5.setBackgroundResource(R.drawable.shape0)
            categoryNameIncome = "Savings"
            categoryNameIncomeM.value = categoryNameIncome
        }
        binding.imageView6.setOnClickListener {
            nullallcategoryincome(binding)
            binding.imageView6.setBackgroundResource(R.drawable.shape0)
            categoryNameIncome = "Other"
            categoryNameIncomeM.value = categoryNameIncome

        }

    }
    fun SelectIncomeBankAccountButton(binding: FragmentNewIncomeBinding) {
        nullaccountall(binding)
        binding.bankAccountButton.setBackgroundResource(R.drawable.button_navi_full)
        println("çalışıyon mu 1")

    }
    fun SelectIncomeCashAccountButton(binding: FragmentNewIncomeBinding){
        nullaccountall(binding)
        binding.cashAccountButton.setBackgroundResource(R.drawable.cash_nav_full)
        println("çalışıyon mu 2")

    }







}