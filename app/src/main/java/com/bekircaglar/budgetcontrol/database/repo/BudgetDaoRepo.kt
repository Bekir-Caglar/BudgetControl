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
import com.bekircaglar.budgetcontrol.model.CashExpenseModel
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
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch




private lateinit var auth : FirebaseAuth
var mail = FirebaseAuth.getInstance().currentUser?.email
class BudgetDaoRepo(
    var amdao: AccountsMoneyDao,
    var bedao:BankexpenseDao,
    var bidao:BankincomeDao,
    var cedao:CashexpenseDao,
    var cidao:CashincomeDao,
    var uddao: UserDataDao) {

    var categoryNameIncome =""

    var userDataM:MutableLiveData<List<UserData>>
    var categoryNameM :MutableLiveData<String>
    var expensesListCashM:MutableLiveData<List<CashExpenseModel>>
    var expenseListBankM:MutableLiveData<List<BankModel>>
    var AccountsMoneyListM:MutableLiveData<List<AccountsMoney>>
    var incomeListCashM:MutableLiveData<List<CashIncomeModel>>
    var incomeListBankM:MutableLiveData<List<BankIncomeModel>>
    var categoryNameIncomeM:MutableLiveData<String>


    init {
        auth = FirebaseAuth.getInstance()
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
        auth = FirebaseAuth.getInstance()
        return expenseListBankM

    }
    fun bringExpenseCashList():MutableLiveData<List<CashExpenseModel>>{
        auth = FirebaseAuth.getInstance()
        return expensesListCashM

    }
    fun bringIncomeBankList():MutableLiveData<List<BankIncomeModel>>{
        auth = FirebaseAuth.getInstance()
        return incomeListBankM

    }
    fun bringIncomeCashList():MutableLiveData<List<CashIncomeModel>>{
        auth = FirebaseAuth.getInstance()
        return incomeListCashM

    }
    fun bringCategoryIncomeNameM():MutableLiveData<String>{
        auth = FirebaseAuth.getInstance()
        return categoryNameIncomeM
    }




    fun expenseButton(viewModel: BudgetFragmentViewModel, binding:FragmentBudgetBinding, context: Context, cashList:ArrayList<CashExpenseModel>, bankList:ArrayList<BankModel>){
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


    fun insertAccountsMoney(newAccountsMoney:AccountsMoney){
        val job = CoroutineScope(Dispatchers.Main).launch {
            auth = FirebaseAuth.getInstance()
            amdao.insertAccountMoney(newAccountsMoney)
        }
    }
    fun getAccountsMoneyByUser(){
        val job = CoroutineScope(Dispatchers.Main).launch {
            auth = FirebaseAuth.getInstance()
            AccountsMoneyListM.value = amdao.getAccountsMoneyByUser(auth.currentUser?.email.toString())
        }
    }
    fun updateAccountsMoneyByUser(updatedCashMoney:String,updatedBankMoney:String){
        val job = CoroutineScope(Dispatchers.Main).launch {
            auth = FirebaseAuth.getInstance()
            amdao.updateAccountMoneyByUser(auth.currentUser?.email.toString(),updatedBankMoney,updatedCashMoney)

        }
    }
    fun getUserData(){
        auth = FirebaseAuth.getInstance()
        val job = CoroutineScope(Dispatchers.Main).launch {
            auth = FirebaseAuth.getInstance()
            userDataM.value = uddao.getUserByMail(auth.currentUser?.email.toString())

        }
    }
    fun updateUserData(userImage:String,userName:String,userPhone:String,userDob:String) {
        val job = CoroutineScope(Dispatchers.Main).launch {
            auth = FirebaseAuth.getInstance()
            uddao.updateUserByMail(userImage,userName,userPhone, auth.currentUser?.email.toString(),userDob)
        }
    }
    fun insertUserData(newUserData:UserData){
        val job = CoroutineScope(Dispatchers.Main).launch {
            uddao.insertUser(newUserData)
        }
    }

    fun getBankexpenseListByUser(){
        auth = FirebaseAuth.getInstance()
        mail = FirebaseAuth.getInstance().currentUser?.email
        val job = CoroutineScope(Dispatchers.Main).launch {
           expenseListBankM.value = (mail?.let { bedao.getBankexpenseListByUser(it) })
            println("burası çalıştı banka expense list db ${expenseListBankM.value}")

        }
    }

    fun getCashexpenseListByUser(){
        auth = FirebaseAuth.getInstance()
        mail = FirebaseAuth.getInstance().currentUser?.email
        val job = CoroutineScope(Dispatchers.Main).launch {
           expensesListCashM.value = mail?.let { cedao.Cashexpenselistdaobyuser(it) }
        }
    }

    fun getBankincomeListByUser(){
        auth = FirebaseAuth.getInstance()
        mail = FirebaseAuth.getInstance().currentUser?.email
        val job = CoroutineScope(Dispatchers.Main).launch {
            incomeListBankM.value = mail?.let { bidao.getBankincomelistbyuser(it) }
        }
    }
    fun getCashincomeListByUser(){
        auth = FirebaseAuth.getInstance()
        mail = FirebaseAuth.getInstance().currentUser?.email
        val job = CoroutineScope(Dispatchers.Main).launch {
            incomeListCashM.value = mail?.let { cidao.Cashincomelistdaobyuser(it) }
        }
    }

    fun addBankexpenseList(bankexpense :BankModel){
        val job = CoroutineScope(Dispatchers.Main).launch {
            bedao.addBankexpenselist(bankexpense)

        }
    }
    fun addCashexpenseList(cashExpense :CashExpenseModel){
        val job = CoroutineScope(Dispatchers.Main).launch {
            cedao.addCashexpenselist(cashExpense)

        }
    }
    fun addBankincomeList(bankincome:BankIncomeModel){
        val job = CoroutineScope(Dispatchers.Main).launch {
            bidao.addBankincomelist(bankincome)

        }
    }
    fun addCasgincomeList(cashincome :CashIncomeModel){
        val job = CoroutineScope(Dispatchers.Main).launch {
            cidao.addCashincomelist(cashincome)

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


    }
    fun SelectIncomeCashAccountButton(binding: FragmentNewIncomeBinding){
        nullaccountall(binding)
        binding.cashAccountButton.setBackgroundResource(R.drawable.cash_nav_full)


    }










}