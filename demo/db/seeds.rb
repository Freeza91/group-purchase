# Seed add you the ability to populate your db.
# We provide you a basic shell for interaction with the end user.
# So try some code like below:
#
#   name = shell.ask("What's your name?")
#   shell.say name
#
email     =  "347212291@qq.com"

password  = "8160332h"

shell.say ""

account = Admin.create(:email => email, :name => "hunter", :surname => "admin", :password => password, :password_confirmation => password, :role => "admin")

if account.valid?
  shell.say "================================================================="
  shell.say "Admin has been successfully created, now you can login with:"
  shell.say "================================================================="
  shell.say "   email: #{email}"
  shell.say "   password: #{password}"
  shell.say "================================================================="
else
  shell.say "Sorry but some thing went wrong!"
  shell.say ""
  account.errors.full_messages.each { |m| shell.say "   - #{m}" }
end

shell.say ""
