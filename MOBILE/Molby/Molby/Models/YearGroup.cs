using System;
using System.Collections.Generic;
using System.Text;

namespace Molby.Models
{
    public class YearGroup
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public DateTime DateStart { get; set; }
        public DateTime DateEnd { get; set; }
        public List<User> Users { get; set; }
    }
}
